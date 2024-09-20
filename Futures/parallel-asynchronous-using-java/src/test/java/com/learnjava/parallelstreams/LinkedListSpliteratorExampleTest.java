package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedListSpliteratorExampleTest {
    private static final ListSpliteratorExample linkedListSpliteratorExample = new ListSpliteratorExample();

    @RepeatedTest(5)
    public void testLinkedListSpliteratorSequentialStream() {
        int size = 1000000;
        LinkedList<Integer> linkedList = DataSet.generateIntegerLinkedList(size);
        List<Integer> result = linkedListSpliteratorExample.multiplyEachValue(linkedList, 2, false);
        assertEquals(size, result.size());
    }

    @RepeatedTest(5)
    public void testLinkedListSpliteratorParallelStream() {
        int size = 1000000;
        LinkedList<Integer> linkedList = DataSet.generateIntegerLinkedList(size);
        List<Integer> result = linkedListSpliteratorExample.multiplyEachValue(linkedList, 2, true);
        assertEquals(size, result.size());
    }
}