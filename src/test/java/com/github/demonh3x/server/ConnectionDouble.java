package com.github.demonh3x.server;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConnectionDouble implements Connection {
    private final InputStream input;
    private final ByteArrayOutputStream output;
    private boolean closed;

    public ConnectionDouble(String input) {
        this.input = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        this.output = new ByteArrayOutputStream();
        this.closed = false;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return output;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return input;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    public byte[] getOutput() {
        return output.toByteArray();
    }

    public boolean isClosed() {
        return closed;
    }
}
