package com.github.demonh3x.server.testdoubles;

import com.github.demonh3x.server.Connection;
import com.github.demonh3x.server.ConnectionHandler;

public class NullConnectionHandler implements ConnectionHandler {
    @Override
    public void handle(Connection client) {}
}
