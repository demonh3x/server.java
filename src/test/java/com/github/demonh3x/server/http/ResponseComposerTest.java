package com.github.demonh3x.server.http;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseComposerTest {
    @Test
    public void formatsA200ResponseIntoHttp() {
        assertThat(
                getComposed(new Response(200, "OK", "Hello client!".getBytes())),
                is(
                        "HTTP/1.1 200 OK\n" +
                                "Content-Length: 13\n" +
                                "\n" +
                                "Hello client!"
                )
        );
    }

    @Test
    public void formatsA404ResponseIntoHttp() {
        assertThat(
                getComposed(new Response(404, "Not Found", "Nothing here".getBytes())),
                is(
                        "HTTP/1.1 404 Not Found\n" +
                                "Content-Length: 12\n" +
                                "\n" +
                                "Nothing here"
                )
        );
    }

    @Test
    public void addsTheHeadersIntoHttp() {
        Response response = new Response(200, "OK", "Body".getBytes(), new HashMap<String, String>(){{
            put("HeaderName1", "headerValue1");
            put("HeaderName2", "headerValue2");
        }});
        assertThat(
                getComposed(response),
                is(
                        "HTTP/1.1 200 OK\n" +
                                "Content-Length: 4\n" +
                                "HeaderName1: headerValue1\n" +
                                "HeaderName2: headerValue2\n" +
                                "\n" +
                                "Body"
                )
        );
    }

    private String getComposed(Response response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            new ResponseComposer().write(response, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(outputStream.toByteArray());
    }
}
