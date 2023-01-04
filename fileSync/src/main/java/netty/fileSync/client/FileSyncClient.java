package netty.fileSync.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import static com.sun.nio.file.SensitivityWatchEventModifier.LOW;
import static java.nio.file.StandardWatchEventKinds.*;
import static netty.fileSync.client.RequestBuilder.buildFileRequest;


/**
 * 文件同步客户端
 *
 * @author chen
 */
public class FileSyncClient {

    private String host;

    private int port;

    private static final WatchEvent.Kind[] kinds = new WatchEvent.Kind[]{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY};

    private List<String> dirRules = Arrays.asList("target", ".git", "node_modules", ".idea");

    private List<String> fileRules = Arrays.asList(".jar", ".class");

    private Channel channel;

    public FileSyncClient() {
        this("175.178.234.47", 8091);
    }

    public FileSyncClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void reconnect(){

    }

    public void start() {

        EventLoopGroup workerGroup;
        Class<? extends SocketChannel> clazz;

        if (Epoll.isAvailable()) {
            workerGroup = new EpollEventLoopGroup();
            clazz = EpollSocketChannel.class;
        } else {
            workerGroup = new NioEventLoopGroup();
            clazz = NioSocketChannel.class;
        }

        Bootstrap b = new Bootstrap();

        b.group(workerGroup)
                .channel(clazz)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(host, port)
                .handler(new netty.fileSync.client.FileSyncClientInitializer());


        ChannelFuture connect = b.connect().syncUninterruptibly();

        channel = connect.channel();

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            syncFiles(watchService);
            syncWatch(watchService);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void syncFiles(WatchService watchService) throws IOException {

        Files.walkFileTree(Path.of("."), new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                for (String rule : fileRules) {
                    if (file.toString().endsWith(rule)) {
                        return FileVisitResult.CONTINUE;
                    }
                }

                channel.writeAndFlush(buildFileRequest(file, 0));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                for (String rule : dirRules) {
                    if (dir.endsWith(rule)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                }

                dir.register(watchService, kinds, LOW);
                channel.writeAndFlush(buildFileRequest(dir, 1));
                return FileVisitResult.CONTINUE;
            }
        });

    }

    private void syncWatch(WatchService watchService) throws InterruptedException, IOException {

        WatchKey key;

        while ((key = watchService.take()) != null) {

            for (WatchEvent<?> watchEvent : key.pollEvents()) {

                WatchEvent.Kind<?> kind = watchEvent.kind();
                Object context = watchEvent.context();
                Watchable watchable = key.watchable();

                Path path = ((Path) watchable).resolve((Path) context);

                int type = Files.isDirectory(path) ? 1 : 0;

                int event = 0;

                if (kind == ENTRY_CREATE) event = 0;
                else if (kind == ENTRY_DELETE) event = 1;
                else if (kind == ENTRY_MODIFY) event = 2;


                if (type == 1) {
                    if (event == 2) {
                        continue;
                    }

                    if (event == 0) {
                        path.register(watchService, kinds, LOW);
                    }

                }

                System.out.println(kind + ":" + path.toAbsolutePath());
                channel.writeAndFlush(buildFileRequest(path, type, event));
            }
            key.reset();
        }
    }

}
