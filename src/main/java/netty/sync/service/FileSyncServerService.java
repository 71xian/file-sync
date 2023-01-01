package netty.sync.service;

import netty.protocols.protobuf.Request;
import netty.sync.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static netty.sync.Constant.*;

/**
 * 服务端文件同步服务
 *
 * @author chen
 */
public class FileSyncServerService {

    public FileSyncServerService() {

        System.out.println(this.getClass().getSimpleName().concat(": init"));

        Path start = Path.of(root);

        try {
            Files.walkFileTree(start, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.size(file) < maxLength) {
                        Files.delete(file);
                    }
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

    /**
     * 对客户端的请求进行处理
     *
     * @param request 客户端发送来的请求
     * @throws Exception
     */
    public void processFile(Request request) throws Exception {

        String s = request.getPath();

        char type = s.charAt(0);

        char event = s.charAt(1);

        // 将路径替换为所处系统支持的路径
        // 因为传递的是相对路径  需要和root路径进行拼接形成完整路径
        Path path = Path.of(Constant.root, s.substring(2).replace(Constant.replaceChar, File.separatorChar));


        switch (event) {
            case CREATE:

                // 文件存在则创建失败
                if (Files.exists(path)) {
                    throw new RuntimeException("文件已存在 无法创建");
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
                    throw new RuntimeException("删除路径不存在");
                }

                // 如果是文件夹 删除需要保证该文件夹是一个空文件夹
                // 如果文件夹不为空则无法删除
                if (type == DIR) {
                    long fileCount = Files.list(path).count();
                    if (fileCount != 0) {
                        throw new RuntimeException("文件夹不为空 无法删除");
                    }
                }

                Files.delete(path);
                break;
            case MODIFY:

                // 文件不存在则无法修改
                if (!Files.exists(path)) {
                    throw new RuntimeException("文件不存在 无法修改");
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
    public void runTask(String... cmd) {
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
