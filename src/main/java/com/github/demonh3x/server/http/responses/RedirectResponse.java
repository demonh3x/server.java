package com.github.demonh3x.server.http.responses;

import com.github.demonh3x.server.http.Response;

import java.util.HashMap;

public class RedirectResponse extends Response {
    public RedirectResponse(final String location) {
        super(302, "Found", new byte[0], new HashMap<String, String>(){{
            put("Location", location);
        }});
    }
}
