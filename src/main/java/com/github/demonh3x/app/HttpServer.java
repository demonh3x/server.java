package com.github.demonh3x.app;

import com.github.demonh3x.app.handlers.AccessEntity;
import com.github.demonh3x.server.ConnectionHandler;
import com.github.demonh3x.server.Server;
import com.github.demonh3x.server.ThreadedHandler;
import com.github.demonh3x.server.http.Http;
import com.github.demonh3x.app.handlers.ServeFiles;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.handlers.Redirect;
import com.github.demonh3x.server.http.router.Router;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.demonh3x.server.http.router.dsl.Routes.*;
import static com.github.demonh3x.server.http.router.dsl.Routes.Method.*;

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
        Configuration config = new Configuration();

        config.setHost("localhost");
        config.setPort(arguments.getPort());
        config.setDirectory(arguments.getDirectory());

        return config;
    }

    private static Server createServer(Configuration config) {
        ExecutorService executor = Executors.newCachedThreadPool();

        ConnectionHandler connectionHandler = new Http(getRoutes(config));
        return new Server(executor, config.getPort(), new ThreadedHandler(executor, connectionHandler));
    }

    private static RequestHandler getRoutes(Configuration config) {
        ServeFiles serveFilesOnDirectory = serveFilesOn(config.getDirectory());
        Redirect redirectHome = new Redirect(getHomeUri(config));
        AccessEntity accessToFormEntity = new AccessEntity();

        return new Router(
                serveFilesOnDirectory,
                on(GET, "/redirect").will(redirectHome),
                onAnyMethodTo("/form").will(accessToFormEntity)
        );
    }

    private static ServeFiles serveFilesOn(String directory) {
        return new ServeFiles(new File(directory));
    }

    private static String getHomeUri(Configuration config) {
        return String.format("http://%s:%d/", config.getHost(), config.getPort());
    }
}
