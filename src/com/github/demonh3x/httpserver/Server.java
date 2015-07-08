package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int port;
    private ServerSocket socket = null;

    public Server(int port) {
        this.port = port;
    }

    public synchronized void start() {
        if (socket != null) return;

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
