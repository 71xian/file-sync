package netty.fileSync.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.fileSync.protocol.TransferProtocol;

public class FileSyncClientHandler extends SimpleChannelInboundHandler<TransferProtocol.FileRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransferProtocol.FileRequest msg) throws Exception {
        System.out.println(msg.getData().toStringUtf8());
    }
}
