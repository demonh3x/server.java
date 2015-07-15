package com.github.demonh3x.server.http.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

public class BasicAuthentication implements RequestHandler {
    private final RequestHandler delegate;

    public BasicAuthentication(RequestHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public Response handle(Request request) {
        String authorization = request.getHeaders().get("Authorization");
        if (!"Basic YWRtaW46aHVudGVyMg==".equals(authorization)) {
            return new Response(401, "Unauthorized", "Authentication required".getBytes());
        } else {
            return delegate.handle(request);
        }
    }
}
