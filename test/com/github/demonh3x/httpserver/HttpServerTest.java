package com.github.demonh3x.httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpServerTest {
    @Test
    public void thePortIsNotUsedBeforeStarting() {
        new Server(9999);
        assertThat(isPortUsed(9999), is(false));
    }

    @Test
    public void thePortIsUsedAfterStartingAndIsReleasedAfterStopping() {
        Server server = new Server(9999);
        server.start();
        assertThat(isPortUsed(9999), is(true));
        server.stop();
        assertThat(isPortUsed(9999), is(false));
    }

    private boolean isPortUsed(int port) {
        Boolean isUsed = null;
        ServerSocket socket = null;

        try {
            socket = new ServerSocket(port);
            isUsed = false;
        } catch (IOException e) {
            isUsed = true;
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }

        return isUsed;
    }
}
