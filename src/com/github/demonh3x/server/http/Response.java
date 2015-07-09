package com.github.demonh3x.server.http;

public class Response {
    private final int statusCode;
    private final String reasonPhrase;
    private final byte[] messageBody;

    public Response(int statusCode, String reasonPhrase, byte[] messageBody) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.messageBody = messageBody;
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
}
