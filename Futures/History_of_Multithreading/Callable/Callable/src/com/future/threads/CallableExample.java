package com.future.threads;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class CallableExample {
    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        System.out.println("Main Start time " + start);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ThreadNameFetcher threadNameFetcher = new ThreadNameFetcher();
        System.out.println(executor.submit(threadNameFetcher));
        Instant end = Instant.now();
        System.out.println("Main End time " + end);
        sleep(300);
        executor.shutdown();
    }
}

