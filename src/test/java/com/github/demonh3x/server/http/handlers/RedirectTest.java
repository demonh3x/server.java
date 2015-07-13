package com.github.demonh3x.server.http.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectTest {
    @Test
    public void redirectsToRoot() {
        Response response = new Redirect("http://localhost:5000/").handle(null);

        assertThat(response.getStatusCode(), is(302));
        assertThat(response.getReasonPhrase(), is("Found"));
        assertThat(response.getMessageBody(), is(new byte[0]));
        assertThat(response.getHeaders().containsKey("Location"), is(true));
        assertThat(response.getHeaders().get("Location"), is("http://localhost:5000/"));
    }
}
