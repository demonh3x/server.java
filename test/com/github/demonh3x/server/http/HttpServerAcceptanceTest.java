package com.github.demonh3x.server.http;

import com.github.demonh3x.server.http.util.HttpBrowser;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpServerAcceptanceTest {
    @Rule
    public TestRule timeout = new Timeout(1000);

    HttpServer server;
    HttpBrowser browser;

    @Before
    public void setUp() {
        server = new HttpServer(5000);
        server.start();

        browser = new HttpBrowser();
        browser.setHost("localhost");
        browser.setPort("5000");
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void respondsHelloWorldMessageWhenRequestingTheRoot() throws IOException {
        browser.get("/");
        assertThat(browser.latestResponseCode, is(200));
        assertThat(browser.latestResponseContentAsString(), containsString("Hello world"));
    }
}
