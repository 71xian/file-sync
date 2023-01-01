package netty.sync.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocols.protobuf.Request;

/**
 * 客户端文件同步handler
 *
 * @author chen
 */
public class FileSyncClientHandler extends SimpleChannelInboundHandler<Request> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        System.out.println("receive: " + msg.getData().toStringUtf8());
    }

}
