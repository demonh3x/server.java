package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;

public interface RequestMatcher {
    boolean isRequestedBy(Request request);
}
