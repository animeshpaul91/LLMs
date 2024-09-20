package com.Animesh.Java.FunctionalProgramming.NumericStreams;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

// Numeric Streams are used for handling streams of Primitive Types. This helps avoid overhead in autoboxing and unboxing.
public class NumericStreamRangesExample {
    public static void main(String[] args) {
        IntStream intStream = IntStream.range(1, 10);
        System.out.println("Total number of elements : " + intStream.count());
        //intStream.forEach(System.out::print); // stream closed error (Can be iterated only once)

        IntStream.range(1, 10).forEach(value -> System.out.print(value + " "));
        System.out.println();
        IntStream.rangeClosed(1, 10).forEach(value -> System.out.print(value + " "));
        System.out.println();
        System.out.println("Total no of elements using rangeClosed : " + IntStream.rangeClosed(1, 10).count());

        LongStream.rangeClosed(1, 10).forEach(value -> System.out.print(value + " "));

        //asDoubleStream
        System.out.println();
        LongStream.rangeClosed(1, 10).asDoubleStream().forEach(value -> System.out.print(value + " "));
    }
}
