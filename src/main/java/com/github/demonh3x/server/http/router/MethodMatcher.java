package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;

public class MethodMatcher implements RequestMatcher {
    private final String expectedMethod;

    public MethodMatcher(String expectedMethod) {
        this.expectedMethod = expectedMethod;
    }

    @Override
    public boolean isRequestedBy(Request request) {
        return expectedMethod.equals(request.getMethod());
    }
}
