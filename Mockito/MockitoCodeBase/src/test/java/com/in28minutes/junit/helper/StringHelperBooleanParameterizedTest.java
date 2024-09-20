package com.in28minutes.junit.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class StringHelperBooleanParameterizedTest {
    private static final StringHelper helper = new StringHelper();

    private final String input;
    private final boolean expectedOutput;

    public StringHelperBooleanParameterizedTest(String input, boolean expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Parameters
    public static Collection<Object[]> testConditions() {
        Object[][] parameters = {
                {"ABCD", false},
                {"ABAB", true},
                {"AB", true},
                {"A", false}
        };
        return Arrays.asList(parameters);
    }

    @Test
    public void testAreFirstAndLastTwoCharactersSame() {
        assertEquals(expectedOutput, helper.areFirstAndLastTwoCharactersTheSame(input));
    }
}
