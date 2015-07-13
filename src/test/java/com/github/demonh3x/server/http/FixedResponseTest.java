package com.github.demonh3x.server.http;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FixedResponseTest {
    @Test
    public void returnsTheResponse() {
        Response response = new Response(200, "OK", new byte[0]);
        assertThat(new FixedResponse(response).handle(new Request("GET", "/")), is(response));
    }
}
