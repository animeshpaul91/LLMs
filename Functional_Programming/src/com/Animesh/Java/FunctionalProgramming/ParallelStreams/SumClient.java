package com.Animesh.Java.FunctionalProgramming.ParallelStreams;

import java.util.stream.IntStream;

public class SumClient {
    public static void main(String[] args) {
        Sum sum = new Sum(10);
        IntStream.rangeClosed(1, 1000)
                .parallel()
                .forEach(sum::performSum); // correct result is : 500500
        System.out.println(sum.getTotal());
    }
}
