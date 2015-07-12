package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

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
