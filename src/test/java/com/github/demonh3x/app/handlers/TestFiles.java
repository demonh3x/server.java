package com.github.demonh3x.app.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestFiles {
    public static void createFile(File root, String path, byte[] content) {
        File file = new File(root, path);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            Files.write(file.toPath(), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFile(File root, String path, String content) {
        createFile(root, path, content.getBytes());
    }

    public static byte[] readFileAsBinary(File root, String path) {
        File file = new File(root, path);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFileInString(File root, String path) {
        return new String(readFileAsBinary(root, path));
    }
}
