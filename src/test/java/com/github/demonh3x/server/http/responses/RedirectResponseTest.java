package com.github.demonh3x.server.http.responses;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RedirectResponseTest {
    @Test
    public void hasStatusCode302() {
        assertThat(new RedirectResponse("/").getStatusCode(), is(302));
    }

    @Test
    public void theReasonPhraseIsFound() {
        assertThat(new RedirectResponse("/").getReasonPhrase(), is("Found"));
    }

    @Test
    public void theBodyIsEmpty() {
        assertThat(new RedirectResponse("/").getMessageBody(), is(new byte[0]));
    }

    @Test
    public void hasTheLocationHeader() {
        assertThat(new RedirectResponse("/").getHeaders().containsKey("Location"), is(true));
        assertThat(new RedirectResponse("/").getHeaders().get("Location"), is("/"));
    }
}
