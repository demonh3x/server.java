package com.github.demonh3x.server.http.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestReader {
    private final InputStreamReader reader;

    public RequestReader(InputStreamReader reader) {
        this.reader = reader;
    }

    public RequestLine readRequestLine() throws IOException {
        return new RequestLine(reader.readLine());
    }

    public Map<String, String> readHeaders() throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        String headerLine = reader.readLine();
        for (; !headerLine.isEmpty(); headerLine = reader.readLine()) {
            Header header = new Header(headerLine);
            headers.put(header.key, header.value);
        }
        return headers;
    }

    public byte[] readMessageBody(Map<String, String> headers) throws IOException {
        String contentLength = headers.get("Content-Length");
        if (contentLength == null)
            return new byte[0];

        int length = Integer.parseInt(contentLength);
        return reader.readNBytes(length);
    }
}
