package com.github.demonh3x.server.http;

import com.github.demonh3x.server.Connection;
import com.github.demonh3x.server.ConnectionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Http implements ConnectionHandler {
    private final RequestHandler requestHandler;

    public Http(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void handle(Connection client) {
        Request request;
        try {
            request = parseRequest(client.getInputStream());
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
            write(client.getOutputStream(), response);
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

    private void write(OutputStream outputStream, Response response) throws IOException {
        String statusLine = String.format(
                "HTTP/1.1 %d %s\n",
                response.getStatusCode(),
                response.getReasonPhrase()
        );
        outputStream.write(statusLine.getBytes());
        String headers = String.format(
                "Content-Length: %d\n",
                response.getMessageBody().length
        );
        outputStream.write(headers.getBytes());
        outputStream.write("\n".getBytes());
        outputStream.write(response.getMessageBody());
    }

    private Request parseRequest(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        String method = scanner.next();
        String uri = scanner.next();
        return new Request(method, uri);
    }
}
