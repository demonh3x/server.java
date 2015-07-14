package com.github.demonh3x.server.http.uri;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Uri {
    private final String uriText;

    public Uri(String uriText) {
        this.uriText = uriText;
    }

    public Map<String, String> getQuery() {
        if (!hasQuery()) {
            return Collections.emptyMap();
        }

        Map<String, String> arguments = new HashMap<>();

        for (String argumentSection : extractArgumentSections()) {
            String[] argumentParts = getArgumentParts(argumentSection);
            String key = argumentParts[0];
            String value = argumentParts[1];
            arguments.put(key, value);
        }

        return arguments;
    }

    private String[] getArgumentParts(String argumentSection) {
        String[] sections = argumentSection.split("=");
        for (int i = 0; i < sections.length; i++) {
            sections[i] = unescape(sections[i]);
        }
        return sections;
    }

    private String unescape(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasQuery() {
        return uriText.contains("?");
    }

    private String[] extractArgumentSections() {
        return extractQuerySection().split("&");
    }

    private String extractQuerySection() {
        String[] parts = uriText.split("\\?");
        return parts[1];
    }
}
