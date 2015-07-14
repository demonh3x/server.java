package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;


public class AccessEntity implements RequestHandler {
    private String content = "";

    public Response handle(Request request) {
        if (is("GET", request)) {
            return ok(content);
        }

        if (is("POST", request) || is("PUT", request)){
            content = new String(request.getMessageBody());
        }

        if (is("DELETE", request)) {
            content = "";
        }

        return ok("");
    }

    private Response ok(String message) {
        return new Response(200, "OK", message.getBytes());
    }

    private boolean is(String method, Request request) {
        return method.equals(request.getMethod());
    }
}
