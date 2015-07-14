package com.github.demonh3x.server.http.uri;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UriTest {
    @Test
    public void noArguments() {
        Uri uri = new Uri("/home");
        assertThat(uri.getQuery(), is(Collections.<String, String>emptyMap()));
    }

    @Test
    public void oneArgumentWithoutSpecialCharacters() {
        Uri uri = new Uri("/home?key=value");
        assertThat(uri.getQuery(), CoreMatchers.<Map<String, String>>is(new HashMap<String, String>() {{
            put("key", "value");
        }}));
    }

    @Test
    public void twoArgumentsWithoutSpecialCharacters() {
        Uri uri = new Uri("/home?key1=value1&key2=value2");
        assertThat(uri.getQuery(), CoreMatchers.<Map<String, String>>is(new HashMap<String, String>() {{
            put("key1", "value1");
            put("key2", "value2");
        }}));
    }

    @Test
    public void oneArgumentWithSpecialCharacters() {
        Uri uri = new Uri("/home?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F");
        assertThat(uri.getQuery(), CoreMatchers.<Map<String, String>>is(new HashMap<String, String>() {{
            put("variable_1", "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?");
        }}));
    }
}
