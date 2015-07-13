package com.github.demonh3x.server.http;

import com.github.demonh3x.server.Connection;
import com.github.demonh3x.server.ConnectionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
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
            request = readRequest(client.getInputStream());
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
            write(response, client.getOutputStream());
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

    private Request readRequest(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        String method = scanner.next();
        String uri = scanner.next();
        return new Request(method, uri);
    }

    private void write(Response response, OutputStream outputStream) throws IOException {
        writeStatusLine(response, outputStream);
        writeHeaders(response, outputStream);
        writeBody(response, outputStream);
    }

    private void writeBody(Response response, OutputStream outputStream) throws IOException {
        outputStream.write("\n".getBytes());
        outputStream.write(response.getMessageBody());
    }

    private void writeHeaders(Response response, OutputStream outputStream) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Length", Integer.toString(response.getMessageBody().length));
        headers.putAll(response.getHeaders());

        for (Map.Entry<String, String> header : headers.entrySet()) {
            String rawHeader = String.format(
                    "%s: %s\n",
                    header.getKey(),
                    header.getValue()
            );
            outputStream.write(rawHeader.getBytes());
        }
    }

    private void writeStatusLine(Response response, OutputStream outputStream) throws IOException {
        String statusLine = String.format(
                "HTTP/1.1 %d %s\n",
                response.getStatusCode(),
                response.getReasonPhrase()
        );
        outputStream.write(statusLine.getBytes());
    }
}
