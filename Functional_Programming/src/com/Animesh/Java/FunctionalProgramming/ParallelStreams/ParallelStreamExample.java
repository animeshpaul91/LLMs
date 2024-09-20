package com.Animesh.Java.FunctionalProgramming.ParallelStreams;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ParallelStreamExample {
    public static long checkPerformanceResult(Supplier<Integer> sum, int numberOfTimes) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfTimes; i++) {
            sum.get();
        }

        long end = System.currentTimeMillis();
        return end - start;
    }

    public static int sum_sequential_stream() {
        return IntStream.rangeClosed(1, 1000000)
                .sum();
    }

    public static int sum_parallel_stream() {
        return IntStream.rangeClosed(1, 1000000)
                .parallel() // splits the data in to multiple parts //775057
                .sum();
    }

    public static void main(String[] args) {
        System.out.println("Number of cores in my System: " + Runtime.getRuntime().availableProcessors());
        long sequentialDuration = checkPerformanceResult(ParallelStreamExample::sum_sequential_stream, 20);
        System.out.println("sequentialDuration in MilliSeconds: " + sequentialDuration);
        long parallelDuration = checkPerformanceResult(ParallelStreamExample::sum_parallel_stream, 20);
        System.out.println("parallelDuration in MilliSeconds: " + parallelDuration);
    }
}
