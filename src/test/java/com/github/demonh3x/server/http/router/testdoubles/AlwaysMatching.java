package com.github.demonh3x.server.http.router.testdoubles;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.router.RequestMatcher;

public class AlwaysMatching implements RequestMatcher {
    @Override
    public boolean isRequestedBy(Request request) {
        return true;
    }
}
