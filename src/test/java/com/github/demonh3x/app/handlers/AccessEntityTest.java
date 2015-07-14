package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Request;
import com.github.demonh3x.server.http.Response;
import com.github.demonh3x.server.http.testdoubles.TestRequest;
import org.junit.Before;
import org.junit.Test;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccessEntityTest {
    AccessEntity accessEntity;

    @Before
    public void setUp() {
        accessEntity = new AccessEntity();
    }

    @Test
    public void emptyResponseWhenGettingWithNoContent() {
        Response response = accessEntity.handle(get());

        assertIsOkWithContent(response, "");
    }

    @Test
    public void okResponseWhenPostWithNoPreviousContent() {
        Response response = accessEntity.handle(post("content to create"));

        assertIsOk(response);
    }

    @Test
    public void respondsWithCreatedContentWhenGetAfterPost() {
        accessEntity.handle(post("content to create"));
        Response response = accessEntity.handle(get());

        assertIsOkWithContent(response, "content to create");
    }

    @Test
    public void okResponseWhenPutWithPreviousContent() {
        accessEntity.handle(post("content to create"));
        Response response = accessEntity.handle(put("updated content"));

        assertIsOk(response);
    }

    @Test
    public void respondsWithUpdatedContentWhenGetAfterPut() {
        accessEntity.handle(post("content to create"));
        accessEntity.handle(put("updated content"));
        Response response = accessEntity.handle(get());

        assertIsOkWithContent(response, "updated content");
    }

    @Test
    public void okResponseWhenDeleteWithPreviousContent() {
        accessEntity.handle(post("content to create"));
        Response response = accessEntity.handle(delete());

        assertIsOk(response);
    }

    @Test
    public void emptyResponseWhenGetAfterDeletedContent() {
        accessEntity.handle(post("content to create"));
        accessEntity.handle(delete());
        Response response = accessEntity.handle(get());

        assertIsOkWithContent(response, "");
    }

    private Request post(String content) {
        return TestRequest.post("", content.getBytes());
    }

    private Request put(String content) {
        return TestRequest.put("", content.getBytes());
    }

    private void assertIsOkWithContent(Response response, String content) {
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(response.getMessageBody(), is(content.getBytes()));
    }

    private void assertIsOk(Response response) {
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(response.getMessageBody(), is(new byte[0]));
    }
}
