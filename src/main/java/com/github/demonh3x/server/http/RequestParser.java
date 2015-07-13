package com.github.demonh3x.server.http;

import java.io.InputStream;
import java.util.Scanner;

public class RequestParser {
    public Request read(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        String method = scanner.next();
        String uri = scanner.next();
        return new Request(method, uri);
    }
}
