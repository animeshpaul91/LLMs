package com.learnjava.parallelstreams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;

public class ListSpliteratorExample {
    public List<Integer> multiplyEachValue(List<Integer> inputList, int multiplier, boolean isParallel) {
        startTimer();
        Stream<Integer> stream = inputList.stream();
        if (isParallel) stream = stream.parallel();
        List<Integer> result = stream
                .map(num -> num * multiplier)
                .collect(Collectors.toList());
        timeTaken();
        stopWatchReset();
        return result;
    }
}
