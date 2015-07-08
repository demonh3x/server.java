package com.github.demonh3x.httpserver;

import java.io.IOException;
import java.net.Socket;

public interface ConnectionHandler {
    void handle(Socket clientConnection) throws IOException;
}
