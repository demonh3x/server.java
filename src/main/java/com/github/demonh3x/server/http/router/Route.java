package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

public class Route implements RequestMatcher, RequestHandler {
    private final RequestMatcher matcher;
    private final RequestHandler handler;

    public Route(RequestMatcher matcher, RequestHandler delegatedHandler) {
        this.matcher = matcher;
        this.handler = delegatedHandler;
    }

    @Override
    public Response handle(Request request) {
        return handler.handle(request);
    }

    @Override
    public boolean isRequestedBy(Request request) {
        return matcher.isRequestedBy(request);
    }
}

