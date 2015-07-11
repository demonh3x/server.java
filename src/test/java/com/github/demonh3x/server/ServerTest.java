package com.github.demonh3x.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerTest {
    @Rule
    public TestRule timeout = new Timeout(1000);

    @Test
    public void thePortIsNotUsedBeforeStarting() {
        createServer(9999);
        assertThat(isPortUsed(9999), is(false));
    }

    @Test
    public void thePortIsUsedAfterStartingAndIsReleasedAfterStopping() {
        Server server = createServer(9999);
        server.start();
        assertThat(isPortUsed(9999), is(true));
        server.stop();
        assertThat(isPortUsed(9999), is(false));
    }

    @Test
    public void doesNotBreakIfStoppedWithoutStarting() {
        Server server = createServer(9999);
        server.stop();
    }

    @Test
    public void doesNotBreakIfStartedSeveralTimesConcurrently() {
        String errors = getStderrDuring(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i ++) {
                    final Server server = createServer(9999);
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

    @Test
    public void canReceiveConnectionsSequentially() {
        Server server = createServer(9999, new ServesNumberOfConnectionsMade());
        server.start();

        int first = getNumberFromServerAt(9999);
        int second = getNumberFromServerAt(9999);
        int third = getNumberFromServerAt(9999);

        server.stop();

        assertThat(first, is(1));
        assertThat(second, is(2));
        assertThat(third, is(3));
    }

    @Test
    public void canReceiveConnectionsConcurrently() {
        ServesNumberOfConnectionsMade handler = new ServesNumberOfConnectionsMade();
        final Server server = createServer(9999, handler);
        server.start();

        String errors = getStderrDuring(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i ++) {
                    runNTimesInParallel(50, new Runnable() {
                        @Override
                        public void run() {
                            getNumberFromServerAt(9999);
                        }
                    });
                }
            }
        });

        server.stop();

        assertThat(errors, is(""));
        assertThat(handler.connectionsMade.get(), is(1000));
    }

    private class ServesNumberOfConnectionsMade implements ConnectionHandler {
        final AtomicInteger connectionsMade = new AtomicInteger(0);

        @Override
        public void handle(Connection client) {
            try {
                client.getOutputStream().write(connectionsMade.incrementAndGet());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int getNumberFromServerAt(int port) {
        try {
            Socket client = new Socket("localhost", port);
            return client.getInputStream().read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Server createServer(int port) {
        return new Server(Executors.newSingleThreadExecutor(), port, new NullConnectionHandler());
    }

    private Server createServer(int port, ConnectionHandler handler) {
        return new Server(Executors.newSingleThreadExecutor(), port, handler);
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
        ServerSocket socket;

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            return true;
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
