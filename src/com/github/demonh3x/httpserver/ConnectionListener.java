package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener {
    private final ServerSocket server;
    private final ConnectionHandler handler;
    private boolean finished;

    public ConnectionListener(ServerSocket server, ConnectionHandler handler) {
        this.server = server;
        this.handler = handler;
        this.finished = false;
    }

    public void waitForConnection() {
        if (finished) return;

        try {
            Socket connection = server.accept();
            handler.handle(connection);
        } catch (IOException e) {
            if (!finished) throw new RuntimeException(e);
        }
    }

    public void finish() {
        finished = true;

        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
