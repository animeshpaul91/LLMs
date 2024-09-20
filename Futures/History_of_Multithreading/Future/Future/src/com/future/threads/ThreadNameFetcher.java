package com.future.threads;

import java.time.Instant;
import java.util.concurrent.*;

import static java.lang.Thread.*;


public class ThreadNameFetcher implements Callable {
    public ThreadNameFetcher() {
    }

    @Override
    public String call() {
        String name = currentThread().getName();
        Instant start = Instant.now();
        System.out.println("Fetcher: " + name + " Start time " + start);
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" Job completed successfully : Name of the thread is" + name);
        Instant end = Instant.now();
        System.out.println("Fetcher: " + name + " End time " + end);
        return name;
    }

}