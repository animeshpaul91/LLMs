package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayListSpliteratorExampleTest {
    private static final ListSpliteratorExample arrayListSpliteratorExample = new ListSpliteratorExample();

    @RepeatedTest(5)
    public void testArrayListSpliteratorSequentialStream() {
        int size = 1000000;
        ArrayList<Integer> arrayList = DataSet.generateArrayList(size);
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(arrayList, 2, false);
        assertEquals(size, result.size());
    }

    @RepeatedTest(5)
    public void testArrayListSpliteratorParallelStream() {
        int size = 1000000;
        ArrayList<Integer> arrayList = DataSet.generateArrayList(size);
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(arrayList, 2, true);
        assertEquals(size, result.size());
    }
}