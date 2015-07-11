package com.github.demonh3x.server.http;

import com.github.demonh3x.server.ConnectionDouble;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionFailingToReadInput extends ConnectionDouble {
    public ConnectionFailingToReadInput() {
        super("");
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Cannot read!");
            }

            @Override
            public int available() throws IOException {
                return 100;
            }
        };
    }
}
