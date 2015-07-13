package com.github.demonh3x.app;

import com.github.demonh3x.server.Server;
import com.github.demonh3x.server.ThreadedHandler;
import com.github.demonh3x.server.http.Http;
import com.github.demonh3x.app.handlers.ServeFiles;

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
        System.out.println(String.format(
                "Server running at port %d, serving the files in %s",
                arguments.getPort(),
                arguments.getDirectory()
        ));
    }
}
