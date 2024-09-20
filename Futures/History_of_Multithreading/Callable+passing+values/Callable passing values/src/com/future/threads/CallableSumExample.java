package com.future.threads;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class CallableSumExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Instant start = Instant.now();
        System.out.println("Main Start time " + start);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Adder adder1 = new Adder(1, 10);
        Future<Integer> future1 = executor.submit(adder1); // spawning 3 threads for each set of 10 numbers
        Adder adder2 = new Adder(11, 20);
        Future<Integer> future2 = executor.submit(adder2);
        Adder adder3 = new Adder(21, 30);
        Future<Integer> future3 = executor.submit(adder3);

        int sum = future1.get() + future2.get() + future3.get(); // all are blocking calls to each callable
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println("Main End time " + end);
        sleep(300);
        executor.shutdown();
    }
}

