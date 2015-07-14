package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import org.junit.Before;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UriMatcherTest {
    UriMatcher matcher;

    @Before
    public void setUp() {
        matcher = new UriMatcher("/index");
    }

    @Test
    public void matchesAGetWithAnIdenticalUri() {
        assertRequestedBy(get("/index"));
    }

    @Test
    public void matchesAPostWithAnIdenticalUri() {
        assertRequestedBy(post("/index"));
    }

    @Test
    public void matchesAPutWithAnIdenticalUri() {
        assertRequestedBy(put("/index"));
    }

    @Test
    public void matchesAUriStartingWithIt() {
        assertRequestedBy(get("/index?k=v"));
    }

    @Test
    public void doesNotMatchAGetWithDifferentUri() {
        assertNotRequestedBy(get("/"));
    }

    @Test
    public void doesNotMatchAPostWithDifferentUri() {
        assertNotRequestedBy(post("/"));
    }

    private void assertRequestedBy(Request request) {
        assertThat(matcher.isRequestedBy(request), is(true));
    }

    private void assertNotRequestedBy(Request request) {
        assertThat(matcher.isRequestedBy(request), is(false));
    }
}
