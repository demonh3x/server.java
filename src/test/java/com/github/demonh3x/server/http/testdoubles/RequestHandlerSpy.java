package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

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
