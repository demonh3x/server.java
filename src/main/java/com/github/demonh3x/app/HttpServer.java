package com.github.demonh3x.app;

import com.github.demonh3x.server.ConnectionHandler;
import com.github.demonh3x.server.Server;
import com.github.demonh3x.server.ThreadedHandler;
import com.github.demonh3x.server.http.Http;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        Configuration config = getConfiguration(args);
        Server server = createServer(config);

        server.start();
        System.out.println(String.format(
                "Server running at port %d, serving the files in %s",
                config.getPort(),
                config.getDirectory()
        ));
    }

    private static Configuration getConfiguration(String[] args) {
        Args arguments = new Args(args);
        return new Configuration("localhost", arguments.getPort(), arguments.getDirectory());
    }

    private static Server createServer(Configuration config) {
        ExecutorService executor = Executors.newCachedThreadPool();

        ConnectionHandler connectionHandler = new Http(EntryPoint.createWith(config));
        return new Server(executor, config.getPort(), new ThreadedHandler(executor, connectionHandler));
    }
}
