package com.github.demonh3x.server.http.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

import java.util.HashMap;


public class Redirect implements RequestHandler {
    private final String location;

    public Redirect(final String location) {
        this.location = location;
    }

    @Override
    public Response handle(Request request) {
        return new Response(302, "Found", new byte[0], new HashMap<String, String>(){{
            put("Location", location);
        }});
    }
}
