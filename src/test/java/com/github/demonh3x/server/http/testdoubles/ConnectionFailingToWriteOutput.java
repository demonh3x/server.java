package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.testdoubles.ConnectionDouble;

import java.io.IOException;
import java.io.OutputStream;

public class ConnectionFailingToWriteOutput extends ConnectionDouble {
    public ConnectionFailingToWriteOutput(String input) {
        super(input);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Cannot write!");
            }
        };
    }
}
