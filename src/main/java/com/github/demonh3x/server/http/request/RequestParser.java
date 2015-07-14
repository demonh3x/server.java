package com.github.demonh3x.server.http.request;

import com.github.demonh3x.server.http.Request;

import java.io.*;
import java.util.Map;

public class RequestParser {
    public Request read(InputStream inputStream) throws IOException {
        RequestReader reader = new RequestReader(new InputStreamReader(inputStream));
        RequestLine requestLine = reader.readRequestLine();
        Map<String, String> headers = reader.readHeaders();
        byte[] messageBody = reader.readMessageBody(headers);

        return new Request(requestLine.method, requestLine.uri, messageBody, headers);
    }
}
