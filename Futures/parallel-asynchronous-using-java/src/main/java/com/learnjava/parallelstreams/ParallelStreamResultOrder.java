package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {


    public static List<Integer> listOrder(List<Integer> inputList) {
        return inputList.parallelStream()
                .map(i -> i * 2)
                .collect(Collectors.toList());

    }

    public static Set<Integer> listOrder_Set(List<Integer> inputList) {
        return inputList.parallelStream()
                .map(i -> i * 2)
                .collect(Collectors.toSet());

    }

    public static Set<Integer> setOrder(Set<Integer> inputList) {
        return inputList.parallelStream()
                .map(i -> i * 2)
                .collect(Collectors.toSet());
    }

    public static List<Integer> setOrder_List(Set<Integer> inputList) {
        return inputList.parallelStream()
                .map(i -> i * 2)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> input = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        log("inputList : " + input);
        List<Integer> result = listOrder(input);
        log("result list : " + result);


        Set<Integer> input1 = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        log("inputSet : " + input1);
        Set<Integer> result1 = setOrder(input1);
        log("result set: " + result1);

        List<Integer> input2 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        log("inputList : " + input2);
        Set<Integer> result2 = listOrder_Set(input2);
        log("result set: " + result2);

        Set<Integer> input3 = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        log("inputSet : " + input3);
        log("result List: " + setOrder_List(input3));
    }
}