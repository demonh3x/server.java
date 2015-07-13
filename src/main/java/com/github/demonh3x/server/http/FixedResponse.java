package com.github.demonh3x.server.http;

public class FixedResponse implements RequestHandler {
    private final Response response;

    public FixedResponse(Response response) {
        this.response = response;
    }

    public Response handle(Request request) {
        return response;
    }
}
