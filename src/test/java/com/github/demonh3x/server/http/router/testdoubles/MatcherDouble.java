package com.github.demonh3x.server.http.router.testdoubles;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.router.RequestMatcher;

public class MatcherDouble implements RequestMatcher {
    private final boolean isRequestedByRequests;
    private Request receivedRequest;

    public MatcherDouble(boolean isRequestedByRequests) {
        this.isRequestedByRequests = isRequestedByRequests;
    }

    @Override
    public boolean isRequestedBy(Request request) {
        this.receivedRequest = request;
        return isRequestedByRequests;
    }

    public Request getReceivedRequest() {
        return receivedRequest;
    }
}
