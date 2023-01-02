package netty.websocket;

import netty.websocket.serve.WebSocketServer;

public class Start {

    public static void main(String[] args) {
        new WebSocketServer().start();
    }
}
