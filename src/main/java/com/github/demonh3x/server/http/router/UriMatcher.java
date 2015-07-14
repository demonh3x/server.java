package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;

public class UriMatcher implements RequestMatcher {
    private final String uri;

    public UriMatcher(String uriBeginning) {
        this.uri = uriBeginning;
    }

    @Override
    public boolean isRequestedBy(Request request) {
        return request.getUri().startsWith(uri);
    }
}
