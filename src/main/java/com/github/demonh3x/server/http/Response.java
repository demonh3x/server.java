package com.github.demonh3x.server.http;

import java.util.Collections;
import java.util.Map;

public class Response {
    private final int statusCode;
    private final String reasonPhrase;
    private final byte[] messageBody;
    private final Map<String, String> headers;

    public Response(int statusCode, String reasonPhrase, byte[] messageBody) {
        this(statusCode, reasonPhrase, messageBody, Collections.<String, String>emptyMap());
    }

    public Response(int statusCode, String reasonPhrase, byte[] messageBody, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.messageBody = messageBody;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
