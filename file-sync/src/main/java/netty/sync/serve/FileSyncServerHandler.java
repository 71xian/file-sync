package netty.sync.serve;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.sync.proto.Request;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.io.File.separatorChar;
import static netty.sync.constants.Constant.*;

/**
 * 服务端文件同步handler
 *
 * @author chen
 */
@ChannelHandler.Sharable
public class FileSyncServerHandler extends SimpleChannelInboundHandler<Request> {

    static {
        try {

            Path start = Path.of(".").normalize();

            Files.walkFileTree(start, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (!dir.equals(start)) {
                        Files.delete(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public FileSyncServerHandler() {
        System.out.println(this.getClass().getSimpleName().concat(": init"));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {

        // 如果文件没有路径 说明传输的是命令
        if (!msg.hasPath()) {
            String cmd = msg.getData().toStringUtf8().trim();
            runTask(cmd.split("\\s"));
        } else {
            System.out.println(msg.getPath());
            processFile(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    /**
     * 对客户端的请求进行处理
     *
     * @param request 客户端发送来的请求
     * @throws IOException
     */
    private void processFile(Request request) throws IOException {

        String s = request.getPath();

        char type = s.charAt(0);

        char event = s.charAt(1);

        // 将路径替换为所处系统支持的路径
        Path path = Path.of(s.substring(2).replace(replaceChar, separatorChar));

        if (event == CREATE) {
            if (type == DIR) {
                Files.createDirectory(path);
            } else {
                Files.createFile(path);
                Files.write(path, request.getData().toByteArray());
            }

        } else if (event == DELETE) {
            Files.delete(path);
        } else if (event == MODIFY) {
            Files.write(path, request.getData().toByteArray());

            if (path.startsWith("src")) {
                if (Files.exists(Path.of("pom.xml"))) {
                    runTask("mvn", "compile");
                }
            }
        }

    }


    /**
     * 运行命令
     *
     * @param cmd 命令参数
     */
    private void runTask(String... cmd) {
        new Thread(buildCmdTask(cmd)).start();
    }


    /**
     * 生成命令任务
     *
     * @param cmd 命令参数
     * @return 命令任务
     */
    private Runnable buildCmdTask(String... cmd) {
        return () -> {
            ProcessBuilder builder = new ProcessBuilder();
            try {
                builder.command(cmd).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
