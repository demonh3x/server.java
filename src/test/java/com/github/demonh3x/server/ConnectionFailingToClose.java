package com.github.demonh3x.server;

import java.io.IOException;

public class ConnectionFailingToClose extends ConnectionDouble {
    public ConnectionFailingToClose(String input) {
        super(input);
    }

    @Override
    public void close() throws IOException {
        throw new IOException("Failed to close!");
    }
}
