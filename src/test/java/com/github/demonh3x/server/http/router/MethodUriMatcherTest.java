package com.github.demonh3x.server.http.router;

import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MethodUriMatcherTest {
    @Test
    public void matchesARequestWithGetMethodAndIndexUri() {
        RequestMatcher matcher = new MethodAndUriMatcher("GET", "/index");

        assertThat(matcher.isRequestedBy(get("/index")), is(true));
    }

    @Test
    public void doesNotMatchARequestWithDifferentMethod() {
        RequestMatcher matcher = new MethodAndUriMatcher("GET", "/index");

        assertThat(matcher.isRequestedBy(post("/index")), is(false));
    }

    @Test
    public void doesNotMatchARequestWithDifferentUri() {
        RequestMatcher matcher = new MethodAndUriMatcher("GET", "/index");

        assertThat(matcher.isRequestedBy(get("/other")), is(false));
    }
}
