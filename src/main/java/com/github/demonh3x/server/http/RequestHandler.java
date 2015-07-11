package com.github.demonh3x.server.http;

public interface RequestHandler {
    Response handle(Request request);
}
