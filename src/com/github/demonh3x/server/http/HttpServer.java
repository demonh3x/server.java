package com.github.demonh3x.server.http;

import com.github.demonh3x.Args;
import com.github.demonh3x.server.Server;
import com.github.demonh3x.server.ThreadedHandler;

import java.io.*;
import java.util.concurrent.Executors;

public class HttpServer extends Server {
    public HttpServer(int port, String root) {
        super(
                Executors.newSingleThreadExecutor(),
                port,
                new ThreadedHandler(
                        Executors.newCachedThreadPool(),
                        new Http(new ServeFiles(new File(root)))
                )
        );
    }

    public static void main(String[] args) throws IOException {
        Args arguments = new Args(args);
        new HttpServer(arguments.getPort(), arguments.getDirectory()).start();
    }
}
