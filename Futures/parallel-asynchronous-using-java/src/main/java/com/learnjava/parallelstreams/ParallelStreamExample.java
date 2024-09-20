package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamExample {

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    public List<String> stringTransform(List<String> namesList, boolean toggle) {
        Stream<String> namesStream = namesList.stream();
        if (toggle) namesStream = namesStream.parallel();
        return namesStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> string_toLowercase(List<String> namesList) {
        return namesList.parallelStream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
        startTimer();
        List<String> result = parallelStreamExample.stringTransform(namesList, true);
        timeTaken();
        log("Result: " + result);
        log("Assignment Solution: " + parallelStreamExample.string_toLowercase(namesList));
    }
}
