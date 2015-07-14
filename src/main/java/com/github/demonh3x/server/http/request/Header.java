package com.github.demonh3x.server.http.request;

public class Header {
    public final String key;
    public final String value;

    public Header(String line) {
        String separator = ":";
        int indexOfSeparator = line.indexOf(separator);
        this.key = line.substring(0, indexOfSeparator).trim();
        this.value = line.substring(indexOfSeparator + separator.length()).trim();
    }
}
