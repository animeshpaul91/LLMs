package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamPerformanceTest {

    ParallelStreamPerformance intStreamExample = new ParallelStreamPerformance();

    @Test
    void sumUsingIntStream() {
        //given

        //when
        int sum = intStreamExample.sumUsingIntStream(1000000, false);
        System.out.println("sum : " + sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingIntStreamParallel() {
        //given

        //when
        int sum = intStreamExample.sumUsingIntStream(1000000, true);
        System.out.println("sum : " + sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingIterate() {
        //given

        //when
        int sum = intStreamExample.sum_using_iterate(1000000, false);
        System.out.println("sum : " + sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingIterateParallel() {
        //given

        //when
        int sum = intStreamExample.sum_using_iterate(1000000, true);
        System.out.println("sum : " + sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingList() {
        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        //when
        int sum = intStreamExample.sum_using_list(inputList, false);
        System.out.println("sum : " + sum);

        //then
        assertEquals(1784293664, sum);
    }

    @Test
    void sumUsingListParallel() {
        //given
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        //when
        int sum = intStreamExample.sum_using_list(inputList, true);
        System.out.println("sum : " + sum);

        //then
        assertEquals(1784293664, sum);
    }

}