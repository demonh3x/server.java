package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;

public class MethodAndUriMatcher implements RequestMatcher {
    private final String method;
    private final String uri;

    public MethodAndUriMatcher(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    @Override
    public boolean isRequestedBy(Request request) {
        return method.equals(request.getMethod()) && uri.equals(request.getUri());
    }
}
