package com.github.demonh3x.server.http;

public class Response {
    private final int statusCode;
    private final String reasonPhrase;
    private final String messageBody;

    public Response(int statusCode, String reasonPhrase, String messageBody) {
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

    public String getMessageBody() {
        return messageBody;
    }
}
