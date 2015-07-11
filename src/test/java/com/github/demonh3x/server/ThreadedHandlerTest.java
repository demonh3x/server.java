package com.github.demonh3x.server;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ThreadedHandlerTest {
    @Test
    public void doesNotScheduleAnythingBeforeHandlingAnyConnection() {
        ExecutorSpy executor = new ExecutorSpy();
        ConnectionHandlerSpy delegatedHandler = new ConnectionHandlerSpy();

        new ThreadedHandler(executor, delegatedHandler);

        assertThat(executor.getReceivedCommands().size(), is(0));
    }

    @Test
    public void schedulesInTheExecutorOnceWhenHandlingOneConnection() {
        ExecutorSpy executor = new ExecutorSpy();
        ConnectionHandlerSpy delegatedHandler = new ConnectionHandlerSpy();
        ThreadedHandler handler = new ThreadedHandler(executor, delegatedHandler);

        handler.handle(new NullConnection());

        assertThat(executor.getReceivedCommands().size(), is(1));
        assertThat(delegatedHandler.getReceivedConnection(), is(nullValue()));
    }

    @Test
    public void whenRunningTheScheduledCommandPassesTheConnectionToTheDelegate() {
        ExecutorSpy executor = new ExecutorSpy();
        ConnectionHandlerSpy delegatedHandler = new ConnectionHandlerSpy();
        ThreadedHandler handler = new ThreadedHandler(executor, delegatedHandler);
        Connection connection = new NullConnection();

        handler.handle(connection);
        executor.getReceivedCommands().get(0).run();

        assertThat(delegatedHandler.getReceivedConnection(), sameInstance(connection));
    }

    @Test
    public void schedulesInAnotherCommandTheSecondConnection() {
        ExecutorSpy executor = new ExecutorSpy();
        ConnectionHandlerSpy delegatedHandler = new ConnectionHandlerSpy();
        ThreadedHandler handler = new ThreadedHandler(executor, delegatedHandler);

        Connection secondConnection = new NullConnection();

        handler.handle(new NullConnection());
        handler.handle(secondConnection);

        executor.getReceivedCommands().get(1).run();
        assertThat(delegatedHandler.getReceivedConnection(), sameInstance(secondConnection));
    }
}
