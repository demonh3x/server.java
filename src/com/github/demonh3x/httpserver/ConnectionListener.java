package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener {
    private final ServerSocket socket;
    private boolean finished;

    public ConnectionListener(ServerSocket socket) {
        this.socket = socket;
        this.finished = false;
    }

    public void waitForConnection() {
        if (finished) return;

        try {
            socket.accept();
        } catch (IOException e) {
            if (!finished) throw new RuntimeException(e);
        }
    }

    public void finish() {
        finished = true;

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
