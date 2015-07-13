package com.github.demonh3x.app;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArgsTest {
    @org.junit.Test
    public void parsesThePort() {
        Args args = new Args(new String[]{"-p", "5000"});
        assertThat(args.getPort(), is(5000));
    }

    @Test
    public void parsesTheDirectory() {
        Args args = new Args(new String[]{"-d", "::the-directory::"});
        assertThat(args.getDirectory(), is("::the-directory::"));
    }
}
