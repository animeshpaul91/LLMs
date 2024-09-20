package com.future.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

//Timer
//Change number of threads

public class FutureExample2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Instant start = Instant.now();

        for (int i = 0; i <= 9; i++) {
            OrderFetcher orderFetcher = new OrderFetcher();
            Future<Integer> future1 = executor.submit(orderFetcher);

            InventoryChecker inventoryChecker = new InventoryChecker(future1.get());
            System.out.println("Order Number is:" + future1.get());
            Future<Boolean> future2 = executor.submit(inventoryChecker);

            PaymentAcceptor paymentAcceptor = new PaymentAcceptor(future2.get());
            System.out.println("IsInventoryPresent:" + future2.get());

            Future<String> future3 = executor.submit(paymentAcceptor);
            System.out.println(future3.get());
        }
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        System.out.println("Total time " + timeElapsed);
        executor.shutdown();


    }

}