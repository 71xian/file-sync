package netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketProtocolInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // websocket是基于http协议的，所以需要使用http编解码器
        pipeline.addLast(new HttpServerCodec())
                // 对写大数据流的支持
                .addLast(new ChunkedWriteHandler())
                // 对http消息的聚合，聚合成FullHttpRequest或FullHttpResponse
                .addLast(new HttpObjectAggregator(1024 * 64));

        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/", null, true));
        pipeline.addLast(WebSocketServerHandler.INSTANCE);

    }
}
