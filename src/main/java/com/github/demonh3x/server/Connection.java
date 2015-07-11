package com.github.demonh3x.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection extends Closeable {
    OutputStream getOutputStream() throws IOException;
    InputStream getInputStream() throws IOException;
    void close() throws IOException;
}
