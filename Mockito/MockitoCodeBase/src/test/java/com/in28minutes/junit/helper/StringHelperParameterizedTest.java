package com.in28minutes.junit.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StringHelperParameterizedTest {

    // AACD => CD ACD => CD CDEF=>CDEF CDAA => CDAA

    private static final StringHelper helper = new StringHelper();

    private final String input;
    private final String expectedOutput;

    public StringHelperParameterizedTest(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Parameters
    public static Collection<String[]> testConditions() {
        String[][] expectedOutputs = {
                {"AACD", "CD"},
                {"ACD", "CD"}};
        return Arrays.asList(expectedOutputs);
    }

    @Test
    public void testTruncateAInFirst2Positions() {
        assertEquals(expectedOutput,
                helper.truncateAInFirst2Positions(input));
    }
}
