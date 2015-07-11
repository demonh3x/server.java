package com.github.demonh3x.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketConnection implements Connection {
    private final Socket socket;

    public SocketConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
