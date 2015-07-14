package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;

public class UriMatcher implements RequestMatcher {
    private final String exactUri;

    public UriMatcher(String exactUri) {
        this.exactUri = exactUri;
    }

    @Override
    public boolean isRequestedBy(Request request) {
        return exactUri.equals(request.getUri());
    }
}
