package com.github.demonh3x.server.http.response;

import com.github.demonh3x.server.http.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ResponseComposer {
    public void write(Response response, OutputStream outputStream) throws IOException {
        ResponseWriter writer = new ResponseWriter(outputStream);

        writer.writeStatusLine(response.getStatusCode(), response.getReasonPhrase());
        writer.writeHeaders(headersWithContentLength(response));
        writer.writeBody(response.getMessageBody());
    }

    private Map<String, String> headersWithContentLength(Response response) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Length", Integer.toString(response.getMessageBody().length));
        headers.putAll(response.getHeaders());
        return headers;
    }
}
