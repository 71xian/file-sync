package netty.sync.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import netty.protocols.protobuf.Request;
import netty.sync.service.FileSyncServerService;

/**
 * 服务端文件同步handler
 *
 * @author chen
 */
public class FileSyncServerHandler extends SimpleChannelInboundHandler<Request> {

    private static FileSyncServerService service;

    private static ChannelGroup group;

    public FileSyncServerHandler() {
        System.out.println(this.getClass().getSimpleName().concat(": init"));

        if (service == null) {
            service = new FileSyncServerService();
        }

        if (group == null) {
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getStackTrace()[0].getMethodName() + ctx.channel());

        if (group.isEmpty()) {
            group.add(ctx.channel());
        } else {
            ctx.close();
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getStackTrace()[0].getMethodName() + ctx.channel());
        group.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        System.out.println(msg.getPath());

        // 如果文件没有路径 说明传输的是命令
        if (!msg.hasPath()) {

            // 命令内容为空则报错
            if (!msg.hasData()) {
                throw new RuntimeException("命令内容为空");
            }

            service.runTask("mvn", "compile");

        } else {
            service.processFile(msg);
        }

    }

}
