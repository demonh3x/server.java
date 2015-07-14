package com.github.demonh3x.server.http.headers;

public class Range {
    public final int firstIncludedIndex;
    public final int lastIncludedIndex;

    public Range(int firstIncludedIndex, int lastIncludedIndex) {
        this.firstIncludedIndex = firstIncludedIndex;
        this.lastIncludedIndex = lastIncludedIndex;
    }

    public static Range parseFrom(String header, int lastAvailableIndex) {
        Integer[] parts = getParts(getBytesRange(header));
        Integer firstIncludedIndex = parts[0];
        Integer lastIncludedIndex = parts[1];

        if (lastIncludedIndex == null) {
            lastIncludedIndex = lastAvailableIndex;
        }

        if (firstIncludedIndex == null) {
            firstIncludedIndex = (lastAvailableIndex +1) - lastIncludedIndex;
            lastIncludedIndex = lastAvailableIndex;
        }

        return new Range(firstIncludedIndex, lastIncludedIndex);
    }

    private static String getBytesRange(String rangeHeaderValue) {
        return rangeHeaderValue.split("bytes=")[1];
    }

    private static Integer[] getParts(String rangeHeaderValue) {
        String value = " " + rangeHeaderValue + " ";
        String[] rangeTextParts = value.split("-");

        Integer[] parts = new Integer[rangeTextParts.length];
        for (int i = 0; i < rangeTextParts.length; i++) {
            String text = rangeTextParts[i].trim();
            parts[i] = text.isEmpty()? null : Integer.parseInt(text);
        }

        return parts;
    }
}
