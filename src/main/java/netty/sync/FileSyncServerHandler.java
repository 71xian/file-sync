package netty.sync;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocols.protobuf.Request;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.io.File.separatorChar;
import static netty.sync.Constant.*;

/**
 * 服务端文件同步handler
 *
 * @author chen
 */
@ChannelHandler.Sharable
public class FileSyncServerHandler extends SimpleChannelInboundHandler<Request> {


    static {

        try {

            Files.walkFileTree(root, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (!dir.equals(root)) {
                        Files.delete(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static final FileSyncServerHandler INSTANCE = new FileSyncServerHandler();

    private FileSyncServerHandler() {
        System.out.println(this.getClass().getSimpleName().concat(": init"));
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {

        // 如果文件没有路径 说明传输的是命令
        if (!msg.hasPath()) {
            System.out.println(msg.getData().toStringUtf8());
            runTask("mvn", "compile");
        } else {
            System.out.println(msg.getPath());
            processFile(msg);
        }

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
        // 因为传递的是相对路径  需要和root路径进行拼接形成完整路径
        Path path = root.resolve(s.substring(2).replace(replaceChar, separatorChar));

        switch (event) {
            case CREATE:

                // 文件存在则创建失败
                if (Files.exists(path)) {
                    System.out.println("文件已存在 无法创建");
                    break;
                }

                if (type == DIR) {
                    Files.createDirectory(path);
                } else {
                    Files.createFile(path);
                    Files.write(path, request.getData().toByteArray());
                }
                break;
            case DELETE:

                // 需要保证删除的路径存在
                // 删除路径不存在返回报错信息
                if (!Files.exists(path)) {
                    System.out.println("删除路径不存在");
                    break;
                }

                // 如果是文件夹 删除需要保证该文件夹是一个空文件夹
                // 如果文件夹不为空则无法删除
                if (type == DIR) {
                    long fileCount = Files.list(path).count();
                    if (fileCount != 0) {
                        System.out.println("文件夹不为空 无法删除");
                        break;
                    }
                }

                Files.delete(path);
                break;
            case MODIFY:

                // 文件不存在则无法修改
                if (!Files.exists(path)) {
                    System.out.println("文件不存在 无法修改");
                    break;
                }

                Files.write(path, request.getData().toByteArray());
                break;
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
