package com.github.demonh3x.httpserver;

import java.net.Socket;

public interface ConnectionHandler {
    void handle(Socket clientConnection);
}
