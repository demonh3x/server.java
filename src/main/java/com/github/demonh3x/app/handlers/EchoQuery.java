package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;
import com.github.demonh3x.server.http.uri.Uri;

import java.util.Map;

public class EchoQuery implements RequestHandler {
    @Override
    public Response handle(Request request) {
        Map<String, String> query = new Uri(request.getUri()).getQuery();
        return new Response(200, "OK", getMessageFor(query).getBytes());
    }

    private String getMessageFor(Map<String, String> query) {
        String message = "";

        for (Map.Entry<String, String> entry : query.entrySet()) {
            message += String.format("%s = %s\n", entry.getKey(), entry.getValue());
        }

        return message;
    }
}
