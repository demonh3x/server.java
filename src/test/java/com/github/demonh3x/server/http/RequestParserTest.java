package com.github.demonh3x.server.http;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestParserTest {
    @Test
    public void receivesAGetRequestToRootUri() {
        Request receivedRequest = readFrom(
                "GET / HTTP/1.1\n"
        );
        assertThat(receivedRequest.getMethod(), is("GET"));
        assertThat(receivedRequest.getUri(), is("/"));
    }

    @Test
    public void receivesAPostRequestToHomeUri() {
        Request receivedRequest = readFrom(
                "POST /home HTTP/1.1\n"
        );
        assertThat(receivedRequest.getMethod(), is("POST"));
        assertThat(receivedRequest.getUri(), is("/home"));
    }

    private Request readFrom(String rawRequest) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(rawRequest.getBytes());
        return new RequestParser().read(inputStream);
    }
}
