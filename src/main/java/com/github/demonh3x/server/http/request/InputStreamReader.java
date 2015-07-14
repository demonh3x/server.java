package com.github.demonh3x.server.http.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReader {
    private final InputStream inputStream;

    public InputStreamReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String readLine() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int lastByte = inputStream.read(); lastByte != -1; lastByte = inputStream.read()) {
            if (lastByte == '\r' && inputStream.read() == '\n') {
                break;
            }
            buffer.write(lastByte);
        }
        return new String(buffer.toByteArray());
    }

    public byte[] readNBytes(int amountOfBytesToRead) throws IOException {
        byte[] buffer = new byte[amountOfBytesToRead];
        int bytesRead = inputStream.read(buffer, 0, amountOfBytesToRead);
        if (bytesRead != amountOfBytesToRead)
            throw new IOException("Unexpected end of stream!");
        return buffer;
    }
}
