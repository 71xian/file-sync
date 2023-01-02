package netty.sync;

import netty.sync.client.FileSyncClient;
import netty.sync.serve.FileSyncServer;

public class Start {

    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("window")) {
            new FileSyncClient().start();
        } else {
            new FileSyncServer().start();
        }
    }
}
