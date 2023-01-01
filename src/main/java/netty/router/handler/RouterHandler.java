package netty.router.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.router.service.RouterService;

/**
 * 路由切换handler
 *
 * @author chen
 */
public class RouterHandler extends ChannelInboundHandlerAdapter {

    private static RouterService service;

    public RouterHandler() {
        if (service == null) {
            service = new RouterService();
        }
        System.out.println(this.getClass().getSimpleName() + " init");
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf buf) {
            byte magic = buf.readByte();
            System.out.println((char) magic);

            // 协议切换
            service.route(magic, ctx.pipeline());
            ctx.pipeline().remove(this);
        }

    }


}
