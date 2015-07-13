package com.github.demonh3x.app;

public class Configuration {
    private String host;
    private int port;
    private String directory;

    public Configuration() {
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDirectory(String directory) {
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
