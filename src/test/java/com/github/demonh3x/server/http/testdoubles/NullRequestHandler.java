package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

public class NullRequestHandler implements RequestHandler {
    @Override
    public Response handle(Request request) {
        return null;
    }
}
