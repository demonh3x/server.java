package com.github.demonh3x.server.http;

import com.github.demonh3x.Args;
import com.github.demonh3x.server.Server;

import java.io.*;
import java.util.concurrent.Executors;

public class HttpServer extends Server {
    private static final RequestHandler HANDLER = new RequestHandler() {
        @Override
        public Response handle(Request request) {
            return new Response(200, "OK", "Hello world!");
        }
    };

    public HttpServer(int port) {
        super(Executors.newSingleThreadExecutor(), port, new Http(HANDLER));
    }

    public static void main(String[] args) throws IOException {
        Args arguments = new Args(args);
        new HttpServer(arguments.getPort()).start();
    }
}
