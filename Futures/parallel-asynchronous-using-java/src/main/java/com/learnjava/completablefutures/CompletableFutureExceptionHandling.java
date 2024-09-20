package com.learnjava.completablefutures;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureExceptionHandling {
    private final HelloWorldService helloWorldService;

    public CompletableFutureExceptionHandling(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingHandle() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });

        String helloWorld = helloCF
                .handle((previous, exception) -> { // handle gets invoked in both scenarios - while exception occurs and happy path (success).
                    log("Previous Value is: " + previous);
                    if (exception != null) {
                        log("Exception Thrown in Hello: " + exception.getMessage());
                        return "";
                    }
                    return previous;
                })
                .thenCombine(worldCF, (helloString, worldString) -> helloString + worldString)
                .handle((previous, exception) -> { // handle gets invoked in both scenarios - while exception occurs and happy path (success).
                    log("Previous Value is: " + previous);
                    if (exception != null) {
                        log("Exception Thrown in world: " + exception);
                        return "";
                    }
                    return previous;
                })
                .thenCombine(hiCF, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionally() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });

        String helloWorld = helloCF
                .thenCombine(worldCF, (helloString, worldString) -> helloString + worldString)
                .thenCombine(hiCF, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .exceptionally(exception -> { // handle gets invoked in both scenarios - while exception occurs and happy path (success).
                    log("Exception Thrown: " + exception.getMessage());
                    return ""; // exceptionally will catch exceptions at any previous stage in the pipeline
                }) // good idea is to place exceptionally at the end of the pipeline.
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingWhenComplete() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });

        String helloWorld = helloCF
                .whenComplete((previous, exception) -> { // handle gets invoked in both scenarios - while exception occurs and happy path (success).
                    log("Previous Value is: " + previous);
                    if (exception != null)
                        log("Exception Thrown in Hello: " + exception.getMessage());
                }) // no mechanism for recovering from exception. As a result control jumps to next handler in pipeline
                .thenCombine(worldCF, (helloString, worldString) -> helloString + worldString)
                .whenComplete((previous, exception) -> { // handle gets invoked in both scenarios - while exception occurs and happy path (success).
                    log("Previous Value is: " + previous);
                    if (exception != null)
                        log("Exception Thrown in world: " + exception);
                })
                .exceptionally(exception -> { // handle gets invoked in both scenarios - while exception occurs and happy path (success).
                    log("Exception Thrown after thenCombine is: " + exception.getMessage());
                    return ""; // exceptionally will catch exceptions at any previous stage in the pipeline
                }) // good idea is to place exceptionally at the end of the pipeline.
                .thenCombine(hiCF, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }
}
