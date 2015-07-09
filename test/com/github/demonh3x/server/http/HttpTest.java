package com.github.demonh3x.server.http;

import com.github.demonh3x.server.ConnectionDouble;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpTest {
    public static final Response NULL_RESPONSE = new Response(200, "OK", new byte[0]);

    @Test
    public void receivesAGetRequestToRootUri() {
        RequestHandlerDouble requestHandler = new RequestHandlerDouble(NULL_RESPONSE);
        new Http(requestHandler).handle(new ConnectionDouble(
                "GET / HTTP/1.1\n"
        ));

        Request receivedRequest = requestHandler.getReceivedRequest();
        assertThat(receivedRequest.getMethod(), is("GET"));
        assertThat(receivedRequest.getUri(), is("/"));
    }

    @Test
    public void receivesAPostRequestToHomeUri() {
        RequestHandlerDouble requestHandler = new RequestHandlerDouble(NULL_RESPONSE);
        new Http(requestHandler).handle(new ConnectionDouble(
                "POST /home HTTP/1.1\n"
        ));

        Request receivedRequest = requestHandler.getReceivedRequest();
        assertThat(receivedRequest.getMethod(), is("POST"));
        assertThat(receivedRequest.getUri(), is("/home"));
    }

    @Test
    public void formatsA200ResponseIntoHttp() {
        RequestHandlerDouble requestHandler = new RequestHandlerDouble(new Response(200, "OK", "Hello client!".getBytes()));
        ConnectionDouble clientConnection = new ConnectionDouble("GET / HTTP/1.1\n");
        new Http(requestHandler).handle(clientConnection);

        String rawResponse = new String(clientConnection.getOutput());
        assertThat(rawResponse, is(
                "HTTP/1.1 200 OK\n" +
                "Content-Length: 13\n" +
                "\n" +
                "Hello client!"
        ));
    }

    @Test
    public void formatsA404ResponseIntoHttp() {
        RequestHandlerDouble requestHandler = new RequestHandlerDouble(new Response(404, "Not Found", "Nothing here".getBytes()));
        ConnectionDouble clientConnection = new ConnectionDouble("GET / HTTP/1.1\n");
        new Http(requestHandler).handle(clientConnection);

        String rawResponse = new String(clientConnection.getOutput());
        assertThat(rawResponse, is(
                "HTTP/1.1 404 Not Found\n" +
                "Content-Length: 12\n" +
                "\n" +
                "Nothing here"
        ));
    }

    @Test
    public void closesTheConnection() {
        RequestHandlerDouble requestHandler = new RequestHandlerDouble(NULL_RESPONSE);
        ConnectionDouble connection = new ConnectionDouble("GET / HTTP/1.1\n");
        new Http(requestHandler).handle(connection);

        assertThat(connection.isClosed(), is(true));
    }
}
