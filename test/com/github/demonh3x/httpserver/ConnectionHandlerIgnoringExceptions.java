package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.Socket;

public abstract class ConnectionHandlerIgnoringExceptions implements ConnectionHandler {
    public abstract void _handle(Socket clientConnection) throws IOException;

    @Override
    public void handle(Socket clientConnection) {
        try {
            _handle(clientConnection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
