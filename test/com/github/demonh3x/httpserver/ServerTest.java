package com.github.demonh3x.httpserver;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerTest {
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

    @Test
    public void doesNotBreakIfStartedTwice() {
        Server server = new Server(9999);
        server.start();
        server.start();
        server.stop();
    }

    @Test
    public void doesNotBreakIfStartedTwiceBySeveralThreads() {
        String errors = getStderrDuring(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i ++) {
                    final Server server = new Server(9999);
                    runNTimesInParallel(10, new Runnable() {
                        @Override
                        public void run() {
                            server.start();
                        }
                    });
                    server.stop();
                }
            }
        });

        assertThat(errors, is(""));
    }

    private void runNTimesInParallel(int times, Runnable action) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            threads.add(new Thread(action));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getStderrDuring(Runnable code) {
        PrintStream stderr = System.err;

        ByteArrayOutputStream errors = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errors));

        code.run();

        System.setErr(stderr);

        return errors.toString();
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
