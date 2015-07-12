package com.github.demonh3x.server.testdoubles;

import com.github.demonh3x.server.Connection;
import com.github.demonh3x.server.ConnectionHandler;

public class ConnectionHandlerSpy implements ConnectionHandler {
    private Connection receivedConnection = null;

    @Override
    public void handle(Connection client) {
        this.receivedConnection = client;
    }

    public Connection getReceivedConnection() {
        return receivedConnection;
    }
}
