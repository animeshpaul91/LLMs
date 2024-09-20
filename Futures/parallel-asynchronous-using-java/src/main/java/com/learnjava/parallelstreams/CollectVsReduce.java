package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class CollectVsReduce {

    public static String collect() {
        List<String> list = DataSet.namesList();
        return list.
                parallelStream().
                        collect(Collectors.joining());
    }

    public static String reduce() {
        List<String> list = DataSet.namesList();
        return list.
                parallelStream().
                reduce("", (s1, s2) -> s1 + s2);
    }

    public static void main(String[] args) {
        log("collect : "+ collect());
        log("reduce : "+ reduce());
    }
}