package com.github.demonh3x.httpserver;

import java.io.IOException;

public abstract class ConnectionHandlerIgnoringExceptions implements ConnectionHandler {
    public abstract void _handle(Connection client) throws IOException;

    @Override
    public void handle(Connection client) {
        try {
            _handle(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
