package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

import java.util.HashMap;

public class AvailableOptions implements RequestHandler {
    @Override
    public Response handle(Request request) {
        return new Response(200, "OK", new byte[0], new HashMap<String, String>(){{
            put("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        }});
    }
}
