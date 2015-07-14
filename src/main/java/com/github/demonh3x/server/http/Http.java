package com.github.demonh3x.server.http;

import com.github.demonh3x.server.Connection;
import com.github.demonh3x.server.ConnectionHandler;
import com.github.demonh3x.server.http.request.RequestParser;

import java.io.IOException;

public class Http implements ConnectionHandler {
    private final RequestHandler requestHandler;
    private final ResponseComposer responseComposer;
    private final RequestParser requestParser;

    public Http(RequestHandler requestHandler) {
        this.requestParser = new RequestParser();
        this.responseComposer = new ResponseComposer();
        this.requestHandler = requestHandler;
    }

    @Override
    public void handle(Connection client) {
        Request request;
        try {
            request = requestParser.read(client.getInputStream());
        } catch (Exception ignored) {
            close(client);
            return;
        }

        Response response;
        try {
            response = requestHandler.handle(request);
        } catch (RuntimeException e) {
            close(client);
            throw e;
        }

        try {
            responseComposer.write(response, client.getOutputStream());
        } catch (Exception ignored) {
        } finally {
            close(client);
        }
    }

    private void close(Connection client) {
        try {
            client.close();
        } catch (IOException ignore) {}
    }
}
