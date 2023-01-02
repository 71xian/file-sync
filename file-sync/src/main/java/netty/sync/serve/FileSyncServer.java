package netty.sync.serve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.sync.initializer.ProtobufInitializer;

public class FileSyncServer {

    private int port;

    public FileSyncServer() {
        this(8091);
    }

    public FileSyncServer(int port) {
        this.port = port;
    }

    public void start() {

        EventLoopGroup bossGroup = new EpollEventLoopGroup(1);
        EventLoopGroup workerGroup = new EpollEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        FileSyncServerHandler fileSyncServerHandler = new FileSyncServerHandler();

        b.group(bossGroup, workerGroup)
                .channel(EpollServerSocketChannel.class)
                .handler(loggingHandler)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ProtobufInitializer());
                        pipeline.addLast(fileSyncServerHandler);
                    }
                });

        Channel channel = b.bind(port).channel();

        channel.closeFuture().addListener(future -> {
            if (future.isSuccess()) {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });

    }
}
