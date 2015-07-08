package com.github.demonh3x.httpserver;

import java.net.Socket;

public class NullConnectionHandler implements ConnectionHandler {
    @Override
    public void handle(Socket clientConnection) {}
}
