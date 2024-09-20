package com.Animesh.Java.Futures.DeadlocksAndSynchronization;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class CompletableFutureRaceConditionLocks {
    private static final ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static int count = 0;

    public static void increment() { // lock on shared variable
        reentrantLock.writeLock().lock();
        count++;
        // System.out.println(count);
        reentrantLock.writeLock().unlock();
    }


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Supplier<String> orderFetcher = () -> {
            increment();
            return "order fetched " + count + " ";
        };

        Function<String, String> orderEnricher = order -> order + "order Enriched " + Thread.currentThread().getName();
        Function<String, String> paymentAcceptor = order -> order + "payment Accepted " + Thread.currentThread().getName();
        Consumer<String> emailSender = System.out::println;

        for (int numOfOrders = 1; numOfOrders <= 10; numOfOrders++) {
            CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(orderEnricher).thenApply(paymentAcceptor).thenAcceptAsync(emailSender, executor);
        }

        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}