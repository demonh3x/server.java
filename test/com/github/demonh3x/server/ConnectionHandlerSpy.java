package com.github.demonh3x.server;

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
