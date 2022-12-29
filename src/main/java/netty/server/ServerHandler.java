package netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import netty.listen.FileWatch;
import netty.listen.FileStruct;

public class ServerHandler extends SimpleChannelInboundHandler<FileStruct> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("delete files");
        FileWatch.deleteFiles();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileStruct msg) throws Exception {
        System.out.println(msg.getPath());
        FileWatch.processFile(msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
//                ctx.writeAndFlush(FileStruct.getDefaultInstance());
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
