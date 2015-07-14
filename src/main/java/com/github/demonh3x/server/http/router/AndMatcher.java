package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;

public class AndMatcher implements RequestMatcher {
    private final RequestMatcher[] matchers;

    public AndMatcher(RequestMatcher... matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean isRequestedBy(Request request) {
        for (RequestMatcher matcher : matchers) {
            if (!matcher.isRequestedBy(request))
                return false;
        }
        return true;
    }
}
