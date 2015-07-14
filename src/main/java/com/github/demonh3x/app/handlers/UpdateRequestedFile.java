package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.RequestHandler;
import com.github.demonh3x.server.http.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UpdateRequestedFile implements RequestHandler {
    private final File root;

    public UpdateRequestedFile(File root) {
        this.root = root;
    }

    @Override
    public Response handle(Request request) {
        File file = new File(root, request.getUri());
        writeFile(file, request.getMessageBody());
        return new Response(204, "No Content", new byte[0]);
    }

    private void writeFile(File file, byte[] content) {
        try {
            Files.write(file.toPath(), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
