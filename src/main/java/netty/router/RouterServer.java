package netty.router;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.router.handler.RouterHandler;

import static netty.sync.Constant.os;

/**
 * 路由服务器
 *
 * @author chen
 */
public class RouterServer {

    private ServerBootstrap b = new ServerBootstrap();

    public RouterServer() {
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        Class<? extends ServerSocketChannel> clazz;

        if (os) {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            clazz = NioServerSocketChannel.class;
        } else {
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup();
            clazz = EpollServerSocketChannel.class;
        }

        b.group(bossGroup, workerGroup)
                .channel(clazz)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RouterHandler());
                    }
                });


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }));
    }


    public void bind(int port) {
        ChannelFuture router = b.bind(port);
        router.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("router start: " + router.channel().localAddress());
            }
        });

    }

}
