package com.github.demonh3x.server.http;

import java.util.Map;

public class Request {
    private final String method;
    private final String uri;
    private final byte[] messageBody;
    private final Map<String, String> headers;
    private final String httpVersion;

    public Request(String method, String uri, String httpVersion, byte[] messageBody, Map<String, String> headers) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
        this.messageBody = messageBody;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
