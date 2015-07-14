package com.github.demonh3x.server.http.router;

import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodMatcherTest {
    @Test
    public void matchesGetWhenConfiguredForGet() {
        MethodMatcher matcher = new MethodMatcher("GET");
        assertThat(matcher.isRequestedBy(get()), is(true));
    }

    @Test
    public void doesNotMatchPostWhenConfiguredForGet() {
        MethodMatcher matcher = new MethodMatcher("GET");
        assertThat(matcher.isRequestedBy(post()), is(false));
    }
}
