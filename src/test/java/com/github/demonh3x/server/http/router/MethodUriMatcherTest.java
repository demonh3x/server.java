package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MethodUriMatcherTest {
    @Test
    public void matchesARequestWithGetMethodAndIndexUri() {
        RequestMatcher matcher = new MethodAndUriMatcher("GET", "/index");

        assertThat(matcher.isRequestedBy(new Request("GET", "/index")), is(true));
    }

    @Test
    public void doesNotMatchARequestWithDifferentMethod() {
        RequestMatcher matcher = new MethodAndUriMatcher("GET", "/index");

        assertThat(matcher.isRequestedBy(new Request("POST", "/index")), is(false));
    }

    @Test
    public void doesNotMatchARequestWithDifferentUri() {
        RequestMatcher matcher = new MethodAndUriMatcher("GET", "/index");

        assertThat(matcher.isRequestedBy(new Request("GET", "/other")), is(false));
    }
}
