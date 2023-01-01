package netty;

import netty.router.RouterServer;
import netty.sync.FileSyncClient;

import static netty.sync.Constant.os;

public class Application {

    public static void main(String[] args) {

        if (os) {
            String host = "175.178.234.47";
            int port = 8091;
            new FileSyncClient().bind(host, port);
        } else {
            new RouterServer().bind(8091);
        }
    }
}
