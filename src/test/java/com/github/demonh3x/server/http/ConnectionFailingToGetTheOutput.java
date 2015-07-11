package com.github.demonh3x.server.http;

import java.io.IOException;
import java.io.OutputStream;

public class ConnectionFailingToGetTheOutput extends ConnectionFailingToWriteOutput {
    public ConnectionFailingToGetTheOutput(String input) {
        super(input);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Cannot get output!");
    }
}
