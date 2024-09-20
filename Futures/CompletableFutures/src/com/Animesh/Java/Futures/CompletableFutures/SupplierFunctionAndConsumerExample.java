package com.Animesh.Java.Futures.CompletableFutures;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

//Timer
//Change number of threads

//OrderFetcher
//PaymentAcceptor
//InventoryChecker

public class SupplierFunctionAndConsumerExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Instant start = Instant.now();
        System.out.println("Start time:" + start);

        Supplier<Integer> orderFetcher = () -> {
            sleep(200);
            //            System.out.println(random);
            return ThreadLocalRandom.current().nextInt(6);
        };

        Function<Integer, Boolean> inventoryChecker = orderNumber -> {
            sleep(200);
            return orderNumber % 2 == 0;
        };

        Consumer<Boolean> paymentAcceptor1 = new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isInventoryPresent) {
                if (isInventoryPresent)
                    System.out.println("payment accepted");
                else
                    System.out.println("payment not accepted");

                Instant end = Instant.now();
                System.out.println("End time:" + end);
            }
        };


        Consumer<Boolean> paymentAcceptor2 = isInventoryPresent -> {
            if (isInventoryPresent)
                System.out.println("payment accepted");
             else
                System.out.println("payment not accepted");

            Instant end = Instant.now();
            System.out.println("End time:" + end);
        };

        //Replace paymentAcceptor1 with paymentAcceptor2, both would give identical output
        for (int i = 0; i <= 9; i++) {
            CompletableFuture.supplyAsync(orderFetcher, executor)
                    .thenApply(inventoryChecker)
                    .thenAccept(paymentAcceptor2); // everything happens in a separate thread
        }

        // main no longer needs to be blocked
        //System.out.println(cf1.join());
        sleep(1000);
        executor.shutdown();

        /* Main Advantages of Completable Futures
        1. Threads no longer have to return to main to report the completion of tasks.
        2. Main thread no longer remains blocked. */
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }
}