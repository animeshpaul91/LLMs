package com.learnjava.completablefutures;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();

        // Two Syntax of Method Reference
        Function<HelloWorldService, String> function = HelloWorldService::hello;
        Supplier<String> hwsSupplier = helloWorldService::hello;

        CompletableFuture.supplyAsync(hwsSupplier) // This will spawn a new Thread from ForkJoin Pool and main thread immediately returns
                .thenApply(String::toUpperCase)
                .thenAccept(result -> log("Result is: " + result)).join(); // join will still block the main thread.
        log("Done");
    }
}
