package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServeFilesTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    ServeFiles serveFiles;

    @Before
    public void setUp() {
        serveFiles = new ServeFiles(testFolder.getRoot());
    }

    @Test
    public void servesTheContentOfAnExistentTextFile() throws IOException {
        createFile("dir/file.txt", "content of the file".getBytes());

        Response response = serveFiles.handle(get("/dir/file.txt"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("content of the file"));
    }

    @Test
    public void servesTheContentOfAnExistentBinaryFile() throws IOException {
        createFile("dir/binary-file.bin", new byte[]{0, 1, 2, -128, 127});

        Response response = serveFiles.handle(get("/dir/binary-file.bin"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(response.getMessageBody(), is(new byte[]{0, 1, 2, -128, 127}));
    }

    @Test
    public void servesTheContentOfAFolder() throws IOException {
        createFile("dir/file1.txt", "content of the file 1".getBytes());
        createFile("dir/file2.txt", "content of the file 2".getBytes());

        Response response = serveFiles.handle(get("/dir"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, containsString("<a href=\"/dir/file1.txt\">file1.txt</a>"));
        assertThat(body, containsString("<a href=\"/dir/file2.txt\">file2.txt</a>"));
        assertThat(body, not(containsString(testFolder.getRoot().getAbsolutePath())));
    }

    @Test
    public void servesTheContentOfSubfolders() throws IOException {
        createFile("parent/child/file.txt", "content of the file".getBytes());

        Response response = serveFiles.handle(get("/parent/child"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, containsString("<a href=\"/parent/child/file.txt\">file.txt</a>"));
        assertThat(body, not(containsString(testFolder.getRoot().getAbsolutePath())));
    }

    @Test
    public void servesA404WhenTheFileDoesNotExist() {
        Response response = serveFiles.handle(get("/dir/non-existent-file.txt"));

        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("File not found."));
    }

    @Test
    public void serves405WhenDoingAPost() {
        Response response = serveFiles.handle(post("/"));

        assertThat(response.getStatusCode(), is(405));
        assertThat(response.getReasonPhrase(), is("Method Not Allowed"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("Action not allowed."));
    }

    @Test
    public void serves405WhenDoingAPut() {
        Response response = serveFiles.handle(put("/"));

        assertThat(response.getStatusCode(), is(405));
        assertThat(response.getReasonPhrase(), is("Method Not Allowed"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("Action not allowed."));
    }

    private void createFile(String path, byte[] content) throws IOException {
        File file = new File(testFolder.getRoot(), path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        Files.write(file.toPath(), content);
    }
}
