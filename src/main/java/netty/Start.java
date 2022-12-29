package netty;

import netty.client.Client;
import netty.server.Server;

import java.io.IOException;
import java.net.URI;

public class Start {

    public static void main(String[] args) throws InterruptedException, IOException {

        System.getenv().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

        if (System.getProperty("os.name").toLowerCase().contains("window")) {
            String remoteAddress = "http://175.178.234.47:8091";
            new Client(URI.create(remoteAddress)).start();
        } else {
            new Server(8091).start();
        }

    }
}
