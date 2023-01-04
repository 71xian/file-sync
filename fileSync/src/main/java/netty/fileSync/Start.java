package netty.fileSync;

import io.netty.channel.epoll.Epoll;
import netty.fileSync.client.FileSyncClient;
import netty.fileSync.server.FileSyncServer;

public class Start {

    public static void main(String[] args) {

        if (Epoll.isAvailable()) {
            new FileSyncServer().start();
        } else {
            new FileSyncClient().start();
        }

    }

}
