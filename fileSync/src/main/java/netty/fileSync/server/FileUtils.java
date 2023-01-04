package netty.fileSync.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void deleteFiles(Path start) throws IOException {
        Files.walk(start).skip(1).sorted((pre, next) -> -1).forEach(path -> {
            try {
                System.out.println(path);
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
