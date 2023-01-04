package netty.fileSync.client;

import com.google.protobuf.ByteString;
import netty.fileSync.protocol.TransferProtocol;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RequestBuilder {

    public static TransferProtocol.FileRequest buildFileRequest(Path path, int type) {
        return buildFileRequest(path, type, 0);
    }

    public static TransferProtocol.FileRequest buildFileRequest(Path path, int type, int event) {

        TransferProtocol.FileRequest.Builder builder = TransferProtocol.FileRequest.newBuilder();

        builder.setType(TransferProtocol.FileType.forNumber(type));

        builder.setEvent(TransferProtocol.FileEvent.forNumber(event));

        builder.setPath(path.toString().replace(File.separatorChar, '#'));

        if (type == 1 || event == 1) {
            return builder.build();
        }

        try {
            builder.setData(ByteString.copyFrom(Files.readAllBytes(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.build();
    }

    public static TransferProtocol.FileRequest buildFileRequest(byte[] bytes) {
        return TransferProtocol.FileRequest.newBuilder()
                .setData(ByteString.copyFrom(bytes))
                .build();
    }
}
