package netty.listen;

import com.google.protobuf.ByteString;
import com.sun.nio.file.SensitivityWatchEventModifier;
import io.netty.channel.Channel;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;
import static netty.listen.FileEvent.MODIFY;

public class FileWatch {

    public static final Map<WatchEvent.Kind<Path>, FileEvent> eventMap = new HashMap<>();

    public static final String[] excludeDir = new String[]{"target", ".git", ".idea"};

    public static final Path root = Path.of(System.getProperty("user.dir"));

    public static final WatchEvent.Kind[] kinds = new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY};


    static {
        for (int i = 0; i < kinds.length; i++) {
            eventMap.put(kinds[i], FileEvent.forNumber(i));
        }
    }

    public static void monitorFiles(Channel channel) throws InterruptedException, IOException {

        WatchService watchService = FileSystems.getDefault().newWatchService();

        // 初始化监听当前目录下的文件夹
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                for (String s : excludeDir) {
                    if (dir.endsWith(s)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                }

                dir.register(watchService, kinds, SensitivityWatchEventModifier.HIGH);
                return FileVisitResult.CONTINUE;
            }

        });

        // 监听当前文件夹
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                Path path = (Path) event.context();
                Path watchable = (Path) key.watchable();
                Path resolve = watchable.resolve(path);

                FileStruct.Builder builder = FileStruct.newBuilder();
                boolean isDirectory = Files.isDirectory(resolve);
                if (isDirectory) {

                    if (event.kind() == ENTRY_MODIFY) {
                        continue;
                    }

                    if (event.kind() == ENTRY_CREATE) {
                        resolve.register(watchService, kinds, SensitivityWatchEventModifier.HIGH);
                    }
                }

                if (event.kind() != ENTRY_DELETE && !isDirectory) {
                    if (Files.exists(resolve)) {
                        builder.setData(ByteString.copyFrom(Files.readAllBytes(resolve)));
                    }
                }

                builder.setType(isDirectory ? FileType.D : FileType.F)
                        .setEvent(eventMap.get(event.kind()))
                        .setPath(root.relativize(resolve).toString().replace(File.separatorChar, '#'));

                channel.writeAndFlush(builder.build());
            }
            key.reset();
        }
    }

    public static Path getActualPath(String path) {
        return root.resolve(path.replace('#', File.separatorChar));
    }

    public static void processFile(FileStruct fileStruct) throws IOException {

        Path path = getActualPath(fileStruct.getPath());

        if (path.endsWith("history")) {
            if (fileStruct.getEvent().equals(MODIFY)) {
                String s = fileStruct.getData().toStringUtf8();
                String[] cmd = s.split("\\s");
                ProcessBuilder builder = new ProcessBuilder();
                builder.directory(root.toFile());
                builder.command(cmd);
                new Thread(() -> {
                    Process start = null;
                    try {
                        start = builder.start();
                        int i = start.waitFor();
                        System.out.println("status: " + i);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }

        switch (fileStruct.getEvent()) {
            case CREATE:
                Files.deleteIfExists(path);
                if (fileStruct.getType().equals(FileType.D)) {
                    Files.createDirectory(path);
                } else {
                    Files.createFile(path);
                    Files.write(path, fileStruct.getData().toByteArray());
                }
                break;
            case DELETE:
                Files.deleteIfExists(path);
                break;
            case MODIFY:
                if (!fileStruct.getType().equals(FileType.D)) {
                    if (Files.exists(path)) {
                        Files.write(path, fileStruct.getData().toByteArray());
                    }
                }
                break;
        }
    }

    public static void deleteFiles() throws IOException {

        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (dir.equals(root)) {
                    return FileVisitResult.CONTINUE;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void transferFiles(Channel channel) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                FileStruct fileStruct = FileStruct.newBuilder()
                        .setType(FileType.F)
                        .setEvent(FileEvent.CREATE)
                        .setPath(root.relativize(file).toString().replace(File.separatorChar, '#'))
                        .setData(ByteString.copyFrom(Files.readAllBytes(file)))
                        .build();

                channel.writeAndFlush(fileStruct);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                for (String s : excludeDir) {
                    if (dir.endsWith(s)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                }

                FileStruct fileStruct = FileStruct.newBuilder()
                        .setType(FileType.D)
                        .setEvent(FileEvent.CREATE)
                        .setPath(root.relativize(dir).toString().replace(File.separatorChar, '#'))
                        .setData(ByteString.empty())
                        .build();

                channel.writeAndFlush(fileStruct);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
