package com.in28minutes.junit.helper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringHelperTest {
    // AACD => CD ACD => CD CDEF=>CDEF CDAA => CDAA
    private static StringHelper helper;

    @Before // runs before every test
    public void setupBeforeTest() {
        helper = new StringHelper(); // this gets invoked/setup before every test executes
        // each test runs with a new instance of StringHelper
    }

    @Test
    public void testTruncateAInFirst2Positions_AinFirst2Positions() {
        assertEquals("CD", helper.truncateAInFirst2Positions("AACD"));
    }

    @Test
    public void testTruncateAInFirst2Positions_AinFirstPosition() {
        assertEquals("CD", helper.truncateAInFirst2Positions("ACD"));
    }

    @Test
    public void testHumanName() {
        assertEquals("NIMESH", helper.truncateAInFirst2Positions("ANIMESH"));
    }

    @Test
    public void testStringWithoutA() {
        assertEquals("CDEF", helper.truncateAInFirst2Positions("CDEF"));
    }

    @Test
    public void testTruncateAInLastTwoPositions() {
        assertEquals("CDEF", helper.truncateAInLast2Positions("CDEFAA"));
    }

    // ABCD => false, ABAB => true, AB => true, A => false
    @Test
    public void testAreFirstAndLastTwoCharactersTheSame_BasicNegativeScenario() {
        assertFalse(helper.areFirstAndLastTwoCharactersTheSame("ABCD"));
    }

    @Test
    public void testAreFirstAndLastTwoCharactersTheSame_BasicPositiveScenario() {
        assertTrue(helper.areFirstAndLastTwoCharactersTheSame("ABAB"));
    }

    @Test
    public void testAreFirstAndLastTwoCharactersTheSameInStringOfLength2() {
        assertTrue(helper.areFirstAndLastTwoCharactersTheSame("AB"));
    }

    @Test
    public void testAreFirstAndLastTwoCharactersTheSameInStringOfLength1() {
        assertFalse(helper.areFirstAndLastTwoCharactersTheSame("A"));
    }
}
