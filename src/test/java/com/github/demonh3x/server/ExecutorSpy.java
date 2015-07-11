package com.github.demonh3x.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ExecutorSpy implements Executor {
    private List<Runnable> receivedCommands = new ArrayList<>();

    @Override
    public void execute(Runnable command) {
        this.receivedCommands.add(command);
    }

    public List<Runnable> getReceivedCommands() {
        return receivedCommands;
    }
}
