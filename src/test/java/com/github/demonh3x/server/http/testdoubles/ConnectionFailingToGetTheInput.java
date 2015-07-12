package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.testdoubles.ConnectionDouble;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionFailingToGetTheInput extends ConnectionDouble {
    public ConnectionFailingToGetTheInput() {
        super("");
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException("Cannot get input!");
    }
}
