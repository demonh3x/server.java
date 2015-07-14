package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;
import com.github.demonh3x.server.http.headers.Range;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ServeFiles implements RequestHandler {
    private final File root;

    public ServeFiles(File root) {
        this.root = root;
    }

    @Override
    public Response handle(Request request) {
        if ("PUT".equals(request.getMethod()) ||
            "POST".equals(request.getMethod())) {
            return new Response(405, "Method Not Allowed", "Action not allowed.".getBytes());
        }

        File file = new File(root, request.getUri());

        if (!file.exists()) {
            return new Response(404, "Not Found", "File not found.".getBytes());
        }

        if (file.isDirectory()) {
            return new Response(200, "OK", getDirectoryContent(file).getBytes());
        }

        if (isRangeRequested(request)) {
            return new Response(206, "OK", readRange(file, request));
        }
        
        return new Response(200, "OK", readFully(file));
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

    private boolean isRangeRequested(Request request) {
        return request.getHeaders().containsKey("Range");
    }

    private byte[] readRange(File file, Request request) {
        Range range = Range.parseFrom(request.getHeaders().get("Range"), (int) file.length() -1);
        return Arrays.copyOfRange(readFully(file), range.firstIncludedIndex, range.lastIncludedIndex +1);
    }

    private byte[] readFully(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
