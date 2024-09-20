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

public class SupplierFunctionAndConsumerExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Instant start = Instant.now();
        //System.out.println("Start time:" + start);

        Supplier<Integer> orderFetcher = () -> {
            sleep(200);
            int random = ThreadLocalRandom.current().nextInt(6);
            System.out.println(random);
            return  random;
        };

        /*Function<Integer, CompletableFuture<Boolean >> inventoryChecker = orderNumber -> {
            sleep(200);
            if (orderNumber % 2 == 0)
                return CompletableFuture.supplyAsync(()->{return Boolean.TRUE;});
            else
                return CompletableFuture.supplyAsync(()-> {return Boolean.FALSE;});
        };*/

        Function<Integer, Boolean > inventoryChecker = orderNumber -> {
            sleep(200);
            return orderNumber % 2 == 0;
        };

        Consumer<Boolean> paymentAcceptor = isInventoryPresent -> {
            if(isInventoryPresent) {
                System.out.println("payment accepted");
            } else{
                System.out.println("payment not accepted");
            }

        };

        CompletableFuture cf1 = CompletableFuture.supplyAsync(orderFetcher,executor)
                                                .thenApply(inventoryChecker)
                                                .thenAccept(paymentAcceptor);
        sleep(500);
        cf1.complete("Payment Not Accepted");
//        cf1.obtrudeValue("Payment Obtruded"); // this state will be added regardless of completion.
        // Manual Completion in case the completion of pipeline is conditional can be used to add a default completion state.
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