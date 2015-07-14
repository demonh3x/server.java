package com.github.demonh3x.server.http.response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseWriter {
    private final OutputStream outputStream;

    public ResponseWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeBody(byte[] messageBody) throws IOException {
        outputStream.write("\n".getBytes());
        outputStream.write(messageBody);
    }

    public void writeHeaders(Map<String, String> headers) throws IOException {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            String rawHeader = String.format(
                    "%s: %s\n",
                    header.getKey(),
                    header.getValue()
            );
            outputStream.write(rawHeader.getBytes());
        }
    }

    public void writeStatusLine(int statusCode, String reasonPhrase) throws IOException {
        String statusLine = String.format(
                "HTTP/1.1 %d %s\n", statusCode, reasonPhrase
        );
        outputStream.write(statusLine.getBytes());
    }
}
