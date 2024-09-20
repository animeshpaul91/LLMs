package com.in28minutes.junit.helper;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class ArraysCompareTest {

    // Assert on Arrays
    @Test
    public void testArraySort_RandomArray() { // testing two Arrays. This matches the contents and not the object in memory
        int[] numbers = {12, 3, 4, 1};
        int[] expected = {1, 3, 4, 12};
        Arrays.sort(numbers);
        assertArrayEquals(expected, numbers);
    }

    // Testing Exceptions
    @Test(expected = NullPointerException.class) // we're expecting a null-pointer exception, always
    public void testArraySort_NullArray() {
        int[] numbers = null;
        Arrays.sort(numbers);
    }

    // Testing Performance
    @Test(timeout = 100)
    public void testSort_Performance() {
        int[] array = {12, 23, 4};
        for (int i = 1; i <= 1000000; i++) {
            array[0] = i;
            Arrays.sort(array);
        }
    }
}
