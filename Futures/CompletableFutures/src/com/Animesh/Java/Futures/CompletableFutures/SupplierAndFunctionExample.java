package com.Animesh.Java.Futures.CompletableFutures;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

//Timer
//Change number of threads

//OrderFetcher
//PaymentAcceptor
//InventoryChecker

public class SupplierAndFunctionExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Supplier<Integer> orderFetcher = () -> {
            sleep(200);
            return new Random().nextInt(6);
        };

        Function<Integer, Boolean> inventoryChecker1 = new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer orderNumber) {
                sleep(200);
                return orderNumber % 2 == 0;
            }
        };

        Function<Integer, Boolean> inventoryCheckerLambda = orderNumber -> {
            sleep(200);
            return orderNumber % 2 == 0;
        };

        //Replace inventoryChecker1 with inventoryCheckerLambda, both would give identical output, either true or false depending on random number
        CompletableFuture<Boolean> cf1 = CompletableFuture.supplyAsync(orderFetcher, executor).thenApply(inventoryCheckerLambda);

        //Inline lambda implementation
        /*CompletableFuture cf2 = CompletableFuture.supplyAsync(orderFetcher,executor).thenApply((orderNumber) -> {
            sleep(200);
            if(orderNumber%2 ==0)
                return  Boolean.TRUE;
            else
                return  Boolean.FALSE;
        };*/

        System.out.println(cf1.get());
        sleep(1000);
        executor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ignored) {
        }
    }
}