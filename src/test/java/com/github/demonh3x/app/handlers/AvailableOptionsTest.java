package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Before;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AvailableOptionsTest {
    AvailableOptions availableOptions;

    @Before
    public void setUp() {
        availableOptions = new AvailableOptions();
    }

    @Test
    public void respondsOkWithTheAllowHeader() {
        Response response = availableOptions.handle(options("/"));
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(response.getHeaders().containsKey("Allow"), is(true));
        assertThat(response.getHeaders().get("Allow"), is("GET,HEAD,POST,OPTIONS,PUT"));
    }
}
