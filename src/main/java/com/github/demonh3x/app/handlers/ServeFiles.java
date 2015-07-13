package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServeFiles implements RequestHandler {
    private final File root;

    public ServeFiles(File root) {
        this.root = root;
    }

    @Override
    public Response handle(Request request) {
        File file = new File(root, request.getUri());

        if (!file.exists()) {
            return new Response(404, "Not Found", "File not found.".getBytes());
        }

        if (file.isDirectory()) {
            return new Response(200, "OK", getDirectoryContent(file).getBytes());
        }

        return new Response(200, "OK", read(file));
    }

    private byte[] read(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDirectoryContent(File directory) {
        String content = "<!DOCTYPE html><html><head><title>Directory: " + directory.getName() +
                "</title><meta charset=\"UTF-8\"></head><body><ul>";

        for (File file : directory.listFiles()) {
            content += "<li>" + linkTo(relativePath(file)) + "</li>";
        }

        content += "</ul></body></html>";

        return content;
    }

    private String linkTo(Path path) {
        return String.format("<a href=\"/%s\">%s</a>", path, path.getFileName());
    }

    private Path relativePath(File file) {
        return root.toPath().relativize(file.toPath());
    }
}
