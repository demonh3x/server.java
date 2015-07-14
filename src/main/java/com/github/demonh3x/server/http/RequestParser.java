package com.github.demonh3x.server.http;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static class RequestLine {
        public final String method;
        public final String uri;
        public final String httpVersion;

        public RequestLine(String line) {
            String[] parts = line.split(" ");
            this.method = parts[0];
            this.uri = parts[1];
            this.httpVersion = parts[2];
        }
    }

    private static class Header {
        public final String key;
        public final String value;

        public Header(String line) {
            String separator = ":";
            int indexOfSeparator = line.indexOf(separator);
            this.key = line.substring(0, indexOfSeparator).trim();
            this.value = line.substring(indexOfSeparator + separator.length()).trim();
        }
    }

    public Request read(InputStream inputStream) throws IOException {
        RequestLine requestLine = readRequestLine(inputStream);
        Map<String, String> headers = readHeaders(inputStream);
        byte[] messageBody = readMessageBody(inputStream, headers);

        return new Request(requestLine.method, requestLine.uri, messageBody, headers);
    }

    private RequestLine readRequestLine(InputStream inputStream) throws IOException {
        return new RequestLine(readLine(inputStream));
    }

    private Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        String headerLine = readLine(inputStream);
        for (; !headerLine.isEmpty(); headerLine = readLine(inputStream)) {
            Header header = new Header(headerLine);
            headers.put(header.key, header.value);
        }
        return headers;
    }

    private byte[] readMessageBody(InputStream inputStream, Map<String, String> headers) throws IOException {
        String contentLength = headers.get("Content-Length");
        if (contentLength == null)
            return new byte[0];

        int length = Integer.parseInt(contentLength);
        return readNBytes(length, inputStream);
    }

    private String readLine(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int lastByte = inputStream.read(); lastByte != -1; lastByte = inputStream.read()) {
            if (lastByte == '\r' && inputStream.read() == '\n') {
                break;
            }
            buffer.write(lastByte);
        }
        return new String(buffer.toByteArray());
    }

    private byte[] readNBytes(int amountOfBytesToRead, InputStream inputStream) throws IOException {
        byte[] buffer = new byte[amountOfBytesToRead];
        int bytesRead = inputStream.read(buffer, 0, amountOfBytesToRead);
        if (bytesRead != amountOfBytesToRead)
            throw new IOException("Unexpected end of stream!");
        return buffer;
    }
}
