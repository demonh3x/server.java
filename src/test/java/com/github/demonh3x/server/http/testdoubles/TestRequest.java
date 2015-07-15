package com.github.demonh3x.server.http.testdoubles;

import com.github.demonh3x.server.http.Request;

import java.util.HashMap;
import java.util.Map;

public class TestRequest {
    public static Request get() {
        return req("GET", "/");
    }

    public static Request get(String uri) {
        return req("GET", uri);
    }

    public static Request get(Map<String, String> headers) {
        return get("/", headers);
    }

    public static Request get(String uri, Map<String, String> headers) {
        return req("GET", uri, new byte[0], headers);
    }

    public static Request post(String uri) {
        return req("POST", uri);
    }

    public static Request post() {
        return req("POST", "/");
    }

    public static Request post(String uri, byte[] messageBody) {
        return req("POST", uri, messageBody);
    }

    public static Request put(String uri) {
        return req("PUT", uri);
    }

    public static Request put(String uri, byte[] messageBody) {
        return req("PUT", uri, messageBody);
    }

    public static Request delete() {
        return req("DELETE", "/");
    }

    public static Request options(String uri) {
        return req("OPTIONS", uri);
    }

    public static Request patch(String uri, byte[] messageBody, Map<String, String> headers) {
        return req("PATCH", uri, messageBody, headers);
    }

    private static Request req(String method, String uri) {
        return req(method, uri, new byte[0], headers());
    }

    private static Request req(String method, String uri, byte[] messageBody) {
        return req(method, uri, messageBody, headers());
    }

    private static Request req(String method, String uri, byte[] messageBody, Map<String, String> headers) {
        return new Request(method, uri, "HTTP/1.1", messageBody, headers);
    }

    public static Map<String, String> headers(String... keyValuePairs) {
        HashMap<String, String> headers = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            headers.put(keyValuePairs[i], keyValuePairs[i+1]);
        }
        return headers;
    }
}
