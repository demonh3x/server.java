package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.http.Request;

import java.util.HashMap;

public class TestRequest {
    public static Request get() {
        return req("GET", "/");
    }

    public static Request get(String uri) {
        return req("GET", uri);
    }

    public static Request post(String uri) {
        return req("POST", uri);
    }

    public static Request post(String uri, byte[] messageBody) {
        return new Request("POST", uri, messageBody, new HashMap<String, String>());
    }

    public static Request put(String uri) {
        return req("PUT", uri);
    }

    public static Request put(String uri, byte[] messageBody) {
        return new Request("PUT", uri, messageBody, new HashMap<String, String>());
    }

    public static Request delete() {
        return req("DELETE", "/");
    }

    private static Request req(String method, String uri) {
        return new Request(method, uri, new byte[0], new HashMap<String, String>());
    }

}
