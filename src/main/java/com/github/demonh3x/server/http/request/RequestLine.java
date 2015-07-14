package com.github.demonh3x.server.http.request;

public class RequestLine {
    public final String method;
    public final String uri;
    public final String httpVersion;

    public RequestLine(String line) {
        String[] parts = line.split(" ");
        this.method = parts[0];
        this.uri = parts[1];
        this.httpVersion = parts[2];
    }
}
