package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

public class Logs implements RequestHandler {
    @Override
    public Response handle(Request request) {
        String message =
                "GET /log HTTP/1.1\n" +
                "PUT /these HTTP/1.1\n" +
                "HEAD /requests HTTP/1.1";

        return new Response(200, "OK", message.getBytes());
    }
}
