package netty.gateway.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {

        if (msg instanceof TextWebSocketFrame frame) {
            System.out.println(frame.text());
            ctx.writeAndFlush(new TextWebSocketFrame(frame.text()));
        } else {
            String message = "unsupported frame type: " + msg.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            System.out.println(((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri());
            ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders().forEach(e -> {
                System.out.println(e.getKey() + ":" + e.getValue());
            });
        }

    }
}
