package com.github.demonh3x.server.http;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final String method;
    private final String uri;
    private final byte[] messageBody;
    private final Map<String, String> headers;

    public Request(String method, String uri, byte[] messageBody, Map<String, String> headers) {
        this.method = method;
        this.uri = uri;
        this.messageBody = messageBody;
        this.headers = headers;
    }

    public Request(String method, String uri) {
        this(method, uri, new byte[0], new HashMap<String, String>());
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
}
