package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.github.demonh3x.app.handlers.TestFiles.createFile;
import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReadRequestedFileTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File root;
    ReadRequestedFile read;

    @Before
    public void setUp() {
        root = testFolder.getRoot();
        read = new ReadRequestedFile(root);
    }

    @Test
    public void servesTheContentOfAnExistentTextFile() throws IOException {
        createFile(root, "dir/file.txt", "content of the file");

        Response response = read.handle(get("/dir/file.txt"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("content of the file"));
    }

    @Test
    public void servesTheContentOfAnExistentBinaryFile() throws IOException {
        createFile(root, "dir/binary-file.bin", new byte[]{0, 1, 2, -128, 127});

        Response response = read.handle(get("/dir/binary-file.bin"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        assertThat(response.getMessageBody(), is(new byte[]{0, 1, 2, -128, 127}));
    }

    @Test
    public void servesTheContentOfAFolder() throws IOException {
        createFile(root, "dir/file1.txt", "content of the file 1");
        createFile(root, "dir/file2.txt", "content of the file 2");

        Response response = read.handle(get("/dir"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, containsString("<a href=\"/dir/file1.txt\">file1.txt</a>"));
        assertThat(body, containsString("<a href=\"/dir/file2.txt\">file2.txt</a>"));
        assertThat(body, not(containsString(root.getAbsolutePath())));
    }

    @Test
    public void servesTheContentOfSubfolders() throws IOException {
        createFile(root, "parent/child/file.txt", "content of the file");

        Response response = read.handle(get("/parent/child"));

        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getReasonPhrase(), is("OK"));
        String body = new String(response.getMessageBody());
        assertThat(body, containsString("<a href=\"/parent/child/file.txt\">file.txt</a>"));
        assertThat(body, not(containsString(root.getAbsolutePath())));
    }

    @Test
    public void servesA404WhenTheFileDoesNotExist() {
        Response response = read.handle(get("/dir/non-existent-file.txt"));

        assertThat(response.getStatusCode(), is(404));
        assertThat(response.getReasonPhrase(), is("Not Found"));
        String body = new String(response.getMessageBody());
        assertThat(body, is("File not found."));
    }

    @Test
    public void servesASpecificSubsectionFromStartToEnd() throws IOException {
        createFile(root, "file.txt", "0 2 4 6");

        Response response = read.handle(get("/file.txt", headers("Range", "bytes=1-4")));
        assertThat(response.getStatusCode(), is(206));
        assertThat(response.getReasonPhrase(), is("Partial Content"));
        assertThat(response.getMessageBody(), is(" 2 4".getBytes()));
    }

    @Test
    public void servesASpecificSubsectionFromStart() throws IOException {
        createFile(root, "file.txt", "0 2 4 6");

        Response response = read.handle(get("/file.txt", headers("Range", "bytes=2-")));
        assertThat(response.getStatusCode(), is(206));
        assertThat(response.getReasonPhrase(), is("Partial Content"));
        assertThat(response.getMessageBody(), is("2 4 6".getBytes()));
    }

    @Test
    public void servesASpecificSubsectionFromEnd() throws IOException {
        createFile(root, "file.txt", "0 2 4 6");

        Response response = read.handle(get("/file.txt", headers("Range", "bytes=-2")));
        assertThat(response.getStatusCode(), is(206));
        assertThat(response.getReasonPhrase(), is("Partial Content"));
        assertThat(response.getMessageBody(), is(" 6".getBytes()));
    }

    @Test
    public void aTextFileHasPlainTextAsTheContentType() {
        assertContentTypeFor("image.txt", "text/plain");
    }

    @Test
    public void aJpegFileHasImageJpegAsTheContentType() {
        assertContentTypeFor("image.jpeg", "image/jpeg");
    }

    @Test
    public void aPngFileHasImagePngAsTheContentType() {
        assertContentTypeFor("image.png", "image/png");
    }

    @Test
    public void aGifFileHasImageGifAsTheContentType() {
        assertContentTypeFor("image.gif", "image/gif");
    }

    private void assertContentTypeFor(String filename, String contentType) {
        Map<String, String> headers = headersRespondedFor(filename);
        assertThat(headers.get("Content-Type"), is(contentType));
    }

    private Map<String, String> headersRespondedFor(String filename) {
        createFile(root, filename, new byte[0]);
        Response response = read.handle(get("/" + filename));
        return response.getHeaders();
    }
}
