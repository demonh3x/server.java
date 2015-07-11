package com.github.demonh3x.server.http;

public class RequestHandlerSpy implements RequestHandler {
    private boolean hasBeenCalled;

    @Override
    public Response handle(Request request) {
        this.hasBeenCalled = true;
        return null;
    }

    public boolean hasBeenCalled() {
        return hasBeenCalled;
    }
}
