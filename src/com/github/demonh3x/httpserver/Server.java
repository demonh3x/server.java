package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;

public class Server {
    private final Executor executor;
    private final int port;
    private final ConnectionHandler connectionHandler;
    private ConnectionListener listener = null;

    public Server(Executor executor, int port, ConnectionHandler connectionHandler) {
        this.executor = executor;
        this.port = port;
        this.connectionHandler = connectionHandler;
    }

    public synchronized void start() {
        if (listener != null) return;

        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listener = new ConnectionListener(socket, connectionHandler);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!listener.isFinished())
                    listener.waitForConnection();
            }
        });
    }

    public void stop() {
        if (listener != null) listener.finish();
    }
}
