package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.get;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogsTest {
    @Test
    public void respondsWithTheExampleLogs() {
        Logs logs = new Logs();
        Response response = logs.handle(get());

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, is(
                "GET /log HTTP/1.1\n" +
                "PUT /these HTTP/1.1\n" +
                "HEAD /requests HTTP/1.1"
        ));
    }
}
