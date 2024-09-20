package com.future.threads;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

public class CallableHelloWorldExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Instant start = Instant.now();
        System.out.println("Main Start time " + start);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Phraser phraser = new Phraser("Hello","World!");
        Future <String> future1 =  executor.submit(phraser);
        System.out.println(future1.get());
        Instant end = Instant.now();
        System.out.println("Main End time " + end);
        sleep(300);
        executor.shutdown();
        }

    }

