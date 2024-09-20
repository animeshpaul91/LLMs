package com.future.threads;

import java.time.Instant;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class CallableExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Instant start = Instant.now();
        System.out.println("Main Start time " + start);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ThreadNameFetcher threadNameFetcher = new ThreadNameFetcher();
        //Introducing Futures

        Future<String> future = executor.submit(threadNameFetcher);
        while (!future.isDone()) {
            // Do something useful in main while callable is executing
            // System.out.println(future.isDone());
            // Busy Waiting
        }
        System.out.println(future.get()); // this is a blocking code. If callable is not over main will wait for the callable to finish
        //System.out.println(future.get(400,TimeUnit.MILLISECONDS));*/
        // this will wait for 400 ms. If callable does not finish, main will proceed

        Instant end = Instant.now();
        System.out.println("Main End time " + end);
        sleep(300);
        executor.shutdown();
    }

}

