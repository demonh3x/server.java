package com.github.demonh3x.httpserver;

import junit.framework.AssertionFailedError;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConnectionListenerTest {
    @Rule
    public TestRule timeout = new Timeout(1000);

    @Test
    public void unblocksAndClosesTheSocketWhenAnotherThreadTellsToFinish() throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(9999);
        final ConnectionListener listener = new ConnectionListener(socket);

        doAfterWaiting(50, new TimerTask() {
            @Override
            public void run() {
                listener.finish();
            }
        });

        listener.waitForConnection();

        assertThat(socket.isClosed(), is(true));
    }

    @Test
    public void doesNotWaitIfToldToFinishBefore() throws IOException {
        ServerSocket socket = new NotAcceptingServerSocketMock(9999);
        final ConnectionListener listener = new ConnectionListener(socket);
        listener.finish();
        listener.waitForConnection();
    }

    private static Timer doAfterWaiting(int milliseconds, final TimerTask action) {
        Timer timer = new Timer();
        timer.schedule(action, milliseconds);
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
}
