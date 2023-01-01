package netty.sync;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.TimeUnit;

import static netty.sync.Constant.os;

/**
 * 文件同步客户端
 *
 * @author chen
 */
public class FileSyncClient {

    /**
     * 客户端线程池
     */
    private Bootstrap b = new Bootstrap();

    /**
     * 客户端文件同步服务 生命周期和客户端绑定
     */
    private FileSyncClientService service = new FileSyncClientService();

    public FileSyncClient() {
        EventLoopGroup workerGroup;
        Class<? extends SocketChannel> clazz;

        if (os) {
            workerGroup = new NioEventLoopGroup();
            clazz = NioSocketChannel.class;
        } else {
            workerGroup = new EpollEventLoopGroup();
            clazz = EpollSocketChannel.class;
        }

        b.group(workerGroup)
                .channel(clazz)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            workerGroup.shutdownGracefully();
            service.stop();
        }));
    }

    /**
     * 绑定服务器域名和端口
     *
     * @param host 域名
     * @param port 端口
     */
    public void bind(String host, int port) {

        ChannelFuture connect = b.connect(host, port);

        connect.addListener(future -> {

            if (future.isSuccess()) {
                Channel channel = connect.channel();

                ByteBuf buf = channel.alloc().buffer(4);

                // 向服务端发送F 请求服务端切换到文件同步服务
                buf.writeBytes("FUCK".getBytes());
                channel.writeAndFlush(buf).sync();
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new ProtobufInitializer());
                pipeline.remove(pipeline.first());

                // 只有设置了FileSyncProtocolInitializer
                // 文件同步服务才能绑定客户端channel
                // 因为文件请求的编解码需要相应的handler支持
                service.bind(channel);
            } else {

                // 建立连接失败尝试重连
                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2L);
                        System.out.println(host + ":" + port + "  connect...");
                        bind(host, port);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        });

    }
}
