package com.github.demonh3x.app.handlers;

import com.github.demonh3x.server.http.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static com.github.demonh3x.app.handlers.TestFiles.createFile;
import static com.github.demonh3x.app.handlers.TestFiles.readFileAsBinary;
import static com.github.demonh3x.server.http.testdoubles.TestRequest.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateRequestedFileTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File root;
    UpdateRequestedFile update;

    @Before
    public void setUp() {
        root = testFolder.getRoot();
        update = new UpdateRequestedFile(root);
    }

    @Test
    public void patchesAFileGivenACorrectSha1() throws IOException {
        createFile(root, "file.txt", "default content");

        byte[] patchedContent = "patched content".getBytes();
        Response response = update.handle(patch(
                "/file.txt",
                patchedContent,
                headers("If-Match", "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec")
        ));

        assertThat(response.getStatusCode(), is(204));
        assertThat(response.getReasonPhrase(), is("No Content"));
        assertThat(response.getMessageBody(), is(new byte[0]));
        assertThat(readFileAsBinary(root, "file.txt"), is(patchedContent));
    }
}
