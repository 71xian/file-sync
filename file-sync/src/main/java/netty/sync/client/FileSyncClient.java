package netty.sync.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.sync.initializer.ProtobufInitializer;


/**
 * 文件同步客户端
 *
 * @author chen
 */
public class FileSyncClient {

    private FileSyncClientService service;

    private String host;

    private int port;

    public FileSyncClient() {
        this("175.178.234.47", 8091);
    }

    public FileSyncClient(String host, int port) {
        this.host = host;
        this.port = port;
        service = new FileSyncClientService();
    }

    public void start() {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();

        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .handler(new ProtobufInitializer());

        ChannelFuture connect = b.connect();

        connect.addListener(future -> {
            if (future.isSuccess()) {
                service.bind(connect.channel());
            }
        });

        connect.channel().closeFuture().addListener(future -> {
            if (future.isSuccess()) {
                service.stop();
                workerGroup.shutdownGracefully();
            }
        });

    }
}
