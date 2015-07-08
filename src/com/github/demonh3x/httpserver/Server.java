package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final int port;
    private ServerSocket socket;
    private boolean running;

    public Server(int port) {
        this.port = port;
        this.running = false;
    }

    public synchronized void start() {
        if (running) return;

        try {
            socket = new ServerSocket(port);
            running = true;
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
