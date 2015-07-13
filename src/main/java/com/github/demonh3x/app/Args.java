package com.github.demonh3x.app;

import java.util.Arrays;
import java.util.List;

public class Args {
    private static final int NOT_FOUND = -1;
    private List<String> arguments;

    public Args(String[] args) {
        arguments = Arrays.asList(args);
    }

    private String getFlagValue(String flag) {
        int index = arguments.indexOf(flag);
        if (index != NOT_FOUND)
            return arguments.get(index +1);

        throw new IllegalStateException(String.format("Flag: %s is not in the arguments!", flag));
    }

    public int getPort() {
        return Integer.parseInt(getFlagValue("-p"));
    }

    public String getDirectory() {
        return getFlagValue("-d");
    }
}
