package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int port;
    private ServerSocket socket;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
