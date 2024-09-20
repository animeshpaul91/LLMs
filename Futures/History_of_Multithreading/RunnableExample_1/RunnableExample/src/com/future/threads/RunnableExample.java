package com.future.threads;

import java.time.Instant;

public class RunnableExample {
    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        System.out.println("Main Start time " + start);
        Thread thread1 = new Thread(new ThreadNamePrinter());
        thread1.start();
        Thread thread2 = new Thread(new ThreadNamePrinter());
        thread2.start();
        Thread thread3 = new Thread(new ThreadNamePrinter());
        thread3.start();
        /*ExecutorService executor = Executors.newFixedThreadPool(4);
        ThreadNamePrinter threadNamePrinter = new ThreadNamePrinter();
        for (int i = 0; i< 3; i++) {
            executor.submit(threadNamePrinter);
        }
        executor.shutdown();*/
    }

}