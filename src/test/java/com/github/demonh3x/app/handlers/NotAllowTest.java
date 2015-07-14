package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Before;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.post;
import static com.github.demonh3x.server.http.testdoubles.TestRequest.put;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotAllowTest {
    NotAllow notAllow;

    @Before
    public void setUp() {
        notAllow = new NotAllow();
    }

    @Test
    public void serves405WhenDoingAPost() {
        Response response = notAllow.handle(post("/"));

        assertThat(response.getStatusCode(), is(405));
        assertThat(response.getReasonPhrase(), is("Method Not Allowed"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("Action not allowed."));
    }

    @Test
    public void serves405WhenDoingAPut() {
        Response response = notAllow.handle(put("/"));

        assertThat(response.getStatusCode(), is(405));
        assertThat(response.getReasonPhrase(), is("Method Not Allowed"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("Action not allowed."));
    }
}
