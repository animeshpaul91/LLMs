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

public class SupplierFunctionAndConsumerExample4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Instant start = Instant.now();
        //System.out.println("Start time:" + start);

        Supplier<Integer> orderFetcher = () -> {
            sleep(200);
            int random = ThreadLocalRandom.current().nextInt(6);
            System.out.println(random);
            return random;
        };

        Function<Integer, CompletableFuture<Boolean>> inventoryChecker = orderNumber -> {
            sleep(200);
            if (orderNumber % 2 == 0)
                return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
            else
                return CompletableFuture.supplyAsync(() -> Boolean.FALSE);
        };

        Consumer<Boolean> paymentAcceptor = isInventoryPresent -> {
            if (isInventoryPresent) {
                System.out.println("payment accepted");
            } else {
                System.out.println("payment not accepted");
            }

        };

        CompletableFuture.supplyAsync(orderFetcher, executor) // supplyAsync returns a CF<Integer>
                .thenCompose(inventoryChecker) // inventoryChecker returns a CF<CF<Boolean>> thenComponse flattens this CF<CF<Boolean>> to Boolean
                .thenAccept(paymentAcceptor);

        sleep(500);
        executor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }
}