package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Before;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EchoQueryTest {
    private EchoQuery echo;

    @Before
    public void setUp() {
        echo = new EchoQuery();
    }

    @Test
    public void respondsTheArgumentsFromTheRequest() {
        Response response = echo.handle(get("/?key%201=value%201&key%202=value%202"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(new String(response.getMessageBody()), is(
                "key 1 = value 1\n" +
                "key 2 = value 2\n"
        ));
    }
}
