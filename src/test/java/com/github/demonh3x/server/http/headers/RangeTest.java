package com.github.demonh3x.server.http.headers;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeTest {
    @Test
    public void fromStartToEnd() {
        Range range = Range.parseFrom("bytes=1-4", 100);
        assertThat(range.firstIncludedIndex, is(1));
        assertThat(range.lastIncludedIndex, is(4));
    }

    @Test
    public void fromStartToUndefinedEnd() {
        Range range = Range.parseFrom("bytes=1-", 100);
        assertThat(range.firstIncludedIndex, is(1));
        assertThat(range.lastIncludedIndex, is(100));
    }

    @Test
    public void fromEnd() {
        Range range = Range.parseFrom("bytes=-1", 100);
        assertThat(range.firstIncludedIndex, is(100));
        assertThat(range.lastIncludedIndex, is(100));
    }
}
