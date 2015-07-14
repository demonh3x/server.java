package com.github.demonh3x.app;

public class Configuration {
    private String host;
    private int port;
    private String directory;

    public Configuration(String host, int port, String directory) {
        this.host = host;
        this.port = port;
        this.directory = directory;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getDirectory() {
        return directory;
    }
}
