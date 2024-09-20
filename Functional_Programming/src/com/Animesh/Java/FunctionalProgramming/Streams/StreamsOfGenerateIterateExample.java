package com.Animesh.Java.FunctionalProgramming.Streams;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamsOfGenerateIterateExample {
    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("adam", "dan", "jenny", "dave"); // create stream of a set of items
        stringStream.forEach(System.out::println);

        List<Integer> integerList = Stream.iterate(1, x -> x * 2) // iterate used to create infinite streams
                .limit(10)
                .collect(toList());

        System.out.println("iterate : " + integerList);
        Supplier<Integer> supplier = () -> new Random().nextInt(10);

        List<Integer> integerList1 = Stream.generate(supplier) // generate used to create infinite streams
                .limit(10)
                .collect(toList());

        System.out.println("generate : " + integerList1);
    }
}
