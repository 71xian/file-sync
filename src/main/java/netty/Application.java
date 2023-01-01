package netty;

import netty.router.RouterServer;
import netty.sync.FileSyncClient;

import java.util.Scanner;

import static netty.sync.Constant.os;

public class Application {

    public static void main(String[] args) {
        if (!os) {
            new RouterServer().bind(8091);
            return;
        }
        System.out.println("please select [1] websocket or [2] fileSync:");
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        scanner.close();
        if (next.equals("1")) {
            System.out.println("websocket is not implement");
        } else {
            String host = "175.178.234.47";
            int port = 8091;
            new FileSyncClient().bind(host, port);
        }
    }
}
