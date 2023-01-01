package netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static WebSocketServerService service;

    public WebSocketServerHandler() {
        System.out.println(this.getClass().getSimpleName().concat(": init"));
        if(service == null){
            service = new WebSocketServerService();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {

        if (msg instanceof TextWebSocketFrame frame) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(frame.text()));
        } else {
            String message = "unsupported frame type: " + msg.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
