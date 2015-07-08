package com.github.demonh3x.httpserver;

import junit.framework.AssertionFailedError;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConnectionListenerTest {
    private final ConnectionHandler NULL_CONNECTION_HANDLER = new ConnectionHandler() {
        @Override
        public void handle(Socket clientConnection) {}
    };

    @Rule
    public TestRule timeout = new Timeout(1000);

    ServerSocket server;
    Queue<Socket> sockets = new ConcurrentLinkedQueue<>();

    @After
    public void tearDown() {
        try {
            if (server != null) server.close();
        } catch (IOException ignored) {}

        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    @Test
    public void isFinishedAfterCallingFinish() throws IOException {
        server = new ServerSocket(9999);
        final ConnectionListener listener = new ConnectionListener(server, NULL_CONNECTION_HANDLER);

        assertThat(listener.isFinished(), is(false));
        listener.finish();
        assertThat(listener.isFinished(), is(true));
    }

    @Test
    public void closesTheServerWhenFinishing() throws IOException {
        server = new ServerSocket(9999);
        final ConnectionListener listener = new ConnectionListener(server, NULL_CONNECTION_HANDLER);

        assertThat(server.isClosed(), is(false));
        listener.finish();
        assertThat(server.isClosed(), is(true));
    }

    @Test
    public void stopsWaitingForConnectionWhenAnotherThreadTellsToFinish() throws IOException, InterruptedException {
        server = new ServerSocket(9999);
        final ConnectionListener listener = new ConnectionListener(server, NULL_CONNECTION_HANDLER);

        doAfterWaiting(50, new Action() {
            @Override
            public void run() {
                listener.finish();
            }
        });

        listener.waitForConnection();
    }

    @Test
    public void doesNotWaitWhenIsFinished() throws IOException {
        server = new NotAcceptingServerSocketMock(9999);
        final ConnectionListener listener = new ConnectionListener(server, NULL_CONNECTION_HANDLER);
        listener.finish();
        listener.waitForConnection();
    }

    @Test
    public void theServerCanReceiveDataFromTheClient() throws IOException {
        server = new ServerSocket(9999);

        final AtomicReference<String> messageForTheServer = new AtomicReference<>(null);
        final ConnectionListener listener = new ConnectionListener(server, new ConnectionHandlerIgnoringExceptions() {
            @Override
            public void _handle(Socket clientConnection) throws IOException {
                String message = readFrom(clientConnection);
                messageForTheServer.set(message);
            }
        });

        doAfterWaiting(50, new Action() {
            @Override
            public void run() throws IOException {
                Socket serverConnection = newSocket("localhost", 9999);
                writeTo(serverConnection, "Hello server! I'm the client!");
                serverConnection.close();
            }
        });

        listener.waitForConnection();

        assertThat(messageForTheServer.get(), is("Hello server! I'm the client!"));
    }

    @Test
    public void theClientCanReceiveDataFromTheServer() throws IOException, InterruptedException {
        server = new ServerSocket(9999);

        final ConnectionListener listener = new ConnectionListener(server, new ConnectionHandlerIgnoringExceptions() {
            @Override
            public void _handle(Socket clientConnection) throws IOException {
                writeTo(clientConnection, "Hello client! I'm the server!");
                clientConnection.close();
            }
        });

        final AtomicReference<String> messageForClient = new AtomicReference<>(null);
        final Semaphore waitForClientToFinishReading = new Semaphore(0);
        doAfterWaiting(50, new Action() {
            @Override
            public void run() throws IOException {
                Socket serverConnection = newSocket("localhost", 9999);
                String message = readFrom(serverConnection);
                messageForClient.set(message);

                waitForClientToFinishReading.release();
            }
        });

        listener.waitForConnection();
        waitForClientToFinishReading.acquire();

        assertThat(messageForClient.get(), is("Hello client! I'm the server!"));
    }

    @Test
    public void isNotFinishedAfterHandlingAConnection() throws IOException {
        server = new ServerSocket(9999);
        final ConnectionListener listener = new ConnectionListener(server, NULL_CONNECTION_HANDLER);

        doAfterWaiting(50, new Action() {
            @Override
            public void run() throws IOException {
                newSocket("localhost", 9999);
            }
        });

        listener.waitForConnection();

        assertThat(listener.isFinished(), is(false));
    }

    abstract class ConnectionHandlerIgnoringExceptions implements ConnectionHandler {
        public abstract void _handle(Socket clientConnection) throws IOException;

        @Override
        public void handle(Socket clientConnection) {
            try {
                _handle(clientConnection);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    interface Action {
        void run() throws Exception;
    }

    private static Timer doAfterWaiting(int milliseconds, final Action action) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    action.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(task, milliseconds);
        return timer;
    }

    class NotAcceptingServerSocketMock extends ServerSocket {
        public NotAcceptingServerSocketMock(int port) throws IOException {
            super(port);
        }

        public Socket accept(){
            throw new AssertionFailedError("Should not be called!");
        }
    }

    private Socket newSocket(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        sockets.add(socket);
        return socket;
    }

    private void writeTo(Socket socket, String output) throws IOException {
        OutputStream stream = socket.getOutputStream();
        stream.write(output.getBytes());
        stream.flush();
    }

    private String readFrom(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        return IOUtils.toString(stream);
    }
}
