package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

        Socket connection;
        try {
            connection = server.accept();
        } catch (IOException e) {
            if (e instanceof SocketException &&
                    "Socket closed".equals(e.getMessage())) {
                finished = true;
                return;
            } else {
                throw new RuntimeException(e);
            }
        }

        try {
            handler.handle(new SocketConnection(connection));
        } catch (RuntimeException e) {
            finish();
            throw e;
        }
    }

    public void finish() {
        finished = true;

        try {
            server.close();
        } catch (IOException ignored) {}
    }

    public boolean isFinished() {
        return finished;
    }
}
