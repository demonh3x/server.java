package com.github.demonh3x.server.http.router;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.router.testdoubles.MatcherDouble;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AndMatcherTest {
    public static final Request REQUEST = get();

    @Test
    public void matchesWhenAllTheMatchersMatch() {
        AndMatcher matcher = new AndMatcher(new MatcherDouble(true), new MatcherDouble(true));
        assertThat(matcher.isRequestedBy(REQUEST), is(true));
    }

    @Test
    public void sendsTheRequestToAllMatchers() {
        MatcherDouble firstMatcher = new MatcherDouble(true);
        MatcherDouble secondMatcher = new MatcherDouble(true);
        AndMatcher matcher = new AndMatcher(firstMatcher, secondMatcher);
        matcher.isRequestedBy(REQUEST);

        assertThat(firstMatcher.getReceivedRequest(), is(REQUEST));
        assertThat(secondMatcher.getReceivedRequest(), is(REQUEST));
    }

    @Test
    public void doesNotMatchWhenOneMatchersDoesNotMatch() {
        AndMatcher matcher = new AndMatcher(new MatcherDouble(true), new MatcherDouble(false));
        assertThat(matcher.isRequestedBy(REQUEST), is(false));
    }

}
