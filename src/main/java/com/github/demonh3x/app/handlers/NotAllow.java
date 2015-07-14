package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

public class NotAllow implements RequestHandler {
    @Override
    public Response handle(Request request) {
        return new Response(405, "Method Not Allowed", "Action not allowed.".getBytes());
    }
}
