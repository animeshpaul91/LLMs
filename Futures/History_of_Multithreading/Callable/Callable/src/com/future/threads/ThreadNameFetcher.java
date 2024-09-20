package com.future.threads;

import java.time.Instant;
import java.util.concurrent.*;

import static java.lang.Thread.*;


public class ThreadNameFetcher implements Callable {
    @Override
    public String call() {
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" Job completed successfully : Name of the thread is" + currentThread().getName());
        Instant end = Instant.now();
        System.out.println("Fetcher End time " + end);
        return currentThread().getName();
    }

}