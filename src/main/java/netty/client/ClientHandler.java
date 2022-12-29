package netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.listen.FileWatch;
import netty.listen.FileStruct;

public class ClientHandler extends SimpleChannelInboundHandler<FileStruct> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("transfer files");
        FileWatch.transferFiles(ctx.channel());

        System.out.println("monitor files");
        FileWatch.monitorFiles(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileStruct msg) throws Exception {
        System.out.println(msg.getPath());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
