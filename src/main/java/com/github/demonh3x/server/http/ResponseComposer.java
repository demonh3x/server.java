package com.github.demonh3x.server.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ResponseComposer {
    public void write(Response response, OutputStream outputStream) throws IOException {
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
