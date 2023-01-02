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

    private Path root = Path.of(".").normalize();

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

        // 需要排除监听的目录
        List<String> dirList = new ArrayList<>();

        String cmd = null;

        // maven
        if (Files.exists(Path.of("pom.xml"))) {
            dirList.add("target");
            dirList.add(".idea");
            cmd = "mvn compile";
        }

        // npm
        if (Files.exists(Path.of("package.json"))) {
            dirList.add("node_modules");
            dirList.add(".vscode");
            cmd = "npm install";
        }

        dirList.add(".git");

        // 将所有文件同步到服务器
        Files.walkFileTree(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                if (Files.size(file) < maxLength) {
                    channel.writeAndFlush(buildFileRequest(file, false));
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                // 该文件夹属于无需上传的文件夹
                for (String excludeDir : dirList) {
                    if (dir.startsWith(excludeDir)) {
                        System.out.println(dir.toAbsolutePath());
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                }

                // 文件夹注册到监听服务上
                dir.register(watchService, kinds, HIGH);

                channel.writeAndFlush(buildFileRequest(dir, true));
                return FileVisitResult.CONTINUE;
            }

        });


        // 发送初始化命令
        Request request = Request.newBuilder()
                .setData(ByteString.copyFrom(cmd.getBytes()))
                .build();

        channel.writeAndFlush(request);

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
                    if (kind == ENTRY_CREATE) {
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

        return builder
                .setPath(filePath)
                .setData(ByteString.copyFrom(Files.readAllBytes(path)))
                .build();
    }

}
