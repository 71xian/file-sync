package netty.sync;

import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import netty.protocols.protobuf.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static com.sun.nio.file.SensitivityWatchEventModifier.HIGH;
import static java.nio.file.StandardWatchEventKinds.*;
import static netty.sync.Constant.*;

/**
 * 客户端文件同步服务
 *
 * @author chen
 */
public class FileSyncClientService {

    /**
     * 文件监听服务
     */
    private WatchService watchService;

    /**
     * 客户端channel 向服务端传递事件请求
     */
    private Channel channel;

    public FileSyncClientService() {
        System.out.println(this.getClass().getSimpleName().concat(": init"));

        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 绑定客户端channel
     *
     * @param channel 客户端channel
     */
    public void bind(Channel channel) {
        this.channel = channel;
        try {
            syncFiles();
            startWatch();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭文件监听服务
     */
    public void stop() {
        try {
            watchService.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 同步文件
     *
     * @throws IOException
     */
    private void syncFiles() throws IOException {


        Path gitignore = root.resolve(".gitignore");

        if (!Files.exists(gitignore)) {
            throw new RuntimeException(".gitignore 文件不存在");
        }

        List<Path> paths = new ArrayList<>();

        List<PathMatcher> matchers = new ArrayList<>();

        FileSystem fileSystem = FileSystems.getDefault();

        Files.readAllLines(gitignore).forEach(line -> {
            char c = line.charAt(0);
            if (!line.isBlank() && c != '#') {
                if (c == '*') {
                    matchers.add(fileSystem.getPathMatcher(line));
                } else {
                    paths.add(root.resolve(line));
                }
            }
        });


        // 遍历根目录 将所有文件发送到服务器
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                for (PathMatcher matcher : matchers) {
                    if (matcher.matches(file)) {
                        return FileVisitResult.CONTINUE;
                    }
                }

                if (Files.size(file) < maxLength) {
                    channel.writeAndFlush(buildFileRequest(file, false));
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                for (Path path : paths) {
                    if (dir.equals(path)) {
                        return FileVisitResult.CONTINUE;
                    }
                }

                // 文件夹注册到监听服务上
                dir.register(watchService, kinds, HIGH);

                channel.writeAndFlush(buildFileRequest(dir, true));
                return FileVisitResult.CONTINUE;
            }

        });

    }

    /**
     * 启动文件监听服务
     *
     * @throws IOException
     * @throws InterruptedException
     */
    private void startWatch() throws IOException, InterruptedException {
        WatchKey key;

        // 每次有文件变动的时候就会响应 否则阻塞
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {

                // 文件事件
                WatchEvent.Kind<?> kind = event.kind();

                // 父路径
                Path parent = (Path) key.watchable();

                // 子路径 相对于父路径的相对路径
                Path child = (Path) event.context();

                // 完整路径
                Path resolve = parent.resolve(child);

                boolean isDir = Files.isDirectory(resolve);

                if (isDir) {

                    // 文件夹的修改事件没什么意义 不发送到服务器
                    if (kind == ENTRY_MODIFY) {
                        continue;
                    }

                    // 文件夹创建之后需要添加到文件监听服务
                    // target文件夹不监听
                    if (kind == ENTRY_CREATE && !resolve.endsWith("target")) {
                        resolve.register(watchService, kinds, HIGH);
                    }

                }

                char eventCode = ' ';
                if (kind == ENTRY_CREATE) {
                    eventCode = CREATE;
                } else if (kind == ENTRY_DELETE) {
                    eventCode = DELETE;
                } else if (kind == ENTRY_MODIFY) {
                    eventCode = MODIFY;
                }

                System.out.println(kind + ":" + resolve);
                channel.writeAndFlush(buildFileRequest(resolve, isDir, eventCode));
            }
            key.reset();
        }
    }

    /**
     * 默认文件请求构建
     *
     * @param path  文件路径
     * @param isDir 是否是文件夹
     * @return 文件请求
     * @throws IOException
     */
    private Request buildFileRequest(Path path, boolean isDir) throws IOException {
        return buildFileRequest(path, isDir, CREATE);
    }

    /**
     * 构建文件请求
     *
     * @param path  文件路径
     * @param isDir 是否是文件夹
     * @param event 文件事件
     * @return 文件请求
     * @throws IOException
     */
    private Request buildFileRequest(Path path, boolean isDir, char event) throws IOException {

        // 获取相对路径
        // 因为服务器的根路径和客户端的根路径不同
        // 但是服务器的相对路径和客户端的相对路径是相同的
        Path relative = root.relativize(path);

        StringBuilder sb = new StringBuilder();

        sb.append(isDir ? DIR : FILE);
        sb.append(event);

        // 服务器的路径分隔符和客户端的路径分隔符可能不同
        // 需要将分隔符转化成一个特殊符号
        // 之后服务器再将这个特殊符号转化成服务器的路径分隔符
        sb.append(relative.toString().replace(File.separatorChar, replaceChar));

        String filePath = sb.toString();

        Request.Builder builder = Request.newBuilder();

        // 目录文件是没有数据的
        // 如果文件被删除了那么也是没有数据的
        if (isDir || event == DELETE) {
            // 目录文件是没有数据的 可以直接发送
            return builder.setPath(filePath).build();
        }

        ByteString data = ByteString.copyFrom(Files.readAllBytes(path));

        // 只有在history文件触发修改才发送命令
        if (filePath.endsWith("history.sh") && event == MODIFY) {
            return builder.setData(data).build();
        }

        return builder.setPath(filePath).setData(data).build();
    }

}
