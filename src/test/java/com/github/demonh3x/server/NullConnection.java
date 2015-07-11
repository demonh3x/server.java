package com.github.demonh3x.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NullConnection implements Connection {
    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
