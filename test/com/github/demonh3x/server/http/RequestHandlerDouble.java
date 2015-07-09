package com.github.demonh3x.server.http;

public class RequestHandlerDouble implements RequestHandler {
    private final Response response;
    private Request receivedRequest;

    public RequestHandlerDouble(Response response) {
        this.response = response;
    }

    @Override
    public Response handle(Request request) {
        this.receivedRequest = request;
        return response;
    }

    public Request getReceivedRequest() {
        return receivedRequest;
    }
}
