package netty.router;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import netty.sync.FileSyncServerHandler;
import netty.sync.ProtobufInitializer;
import netty.websocket.WebSocketProtocolInitializer;

/**
 * 路由切换handler
 *
 * @author chen
 */
@ChannelHandler.Sharable
public class RouterHandler extends ChannelInboundHandlerAdapter {

    public RouterHandler() {
        System.out.println(this.getClass().getSimpleName() + " init");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf buf) {
            if (buf.readableBytes() < 4) {
                return;
            }

            byte magic = buf.getByte(0);
            System.out.println((char) magic);

            ChannelPipeline pipeline = ctx.pipeline();

            if (magic == 'F') {

                pipeline.addLast(new ProtobufInitializer());
                pipeline.addLast(FileSyncServerHandler.INSTANCE);

            } else if (magic == 'G') {

                pipeline.addLast(new WebSocketProtocolInitializer());

                // 将消息继续传递下去
                ctx.fireChannelRead(msg);

            } else {
                ctx.close();
            }

            pipeline.remove(this);
        }
    }

}
