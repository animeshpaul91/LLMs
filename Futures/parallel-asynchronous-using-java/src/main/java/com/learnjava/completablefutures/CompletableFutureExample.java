package com.learnjava.completablefutures;

import com.learnjava.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureExample {

    private final HelloWorldService helloWorldService;
    private final Supplier<String> helloSupplier;
    private final Supplier<String> worldSupplier;

    public CompletableFutureExample() {
        this.helloWorldService = new HelloWorldService();
        this.helloSupplier = helloWorldService::hello;
        this.worldSupplier = helloWorldService::world;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(helloSupplier) // This will spawn a new Thread from ForkJoin Pool and main thread immediately returns
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorldWithSize() {
        return CompletableFuture.supplyAsync(helloSupplier) // This will spawn a new Thread from ForkJoin Pool and main thread immediately returns
                .thenApply(string -> string.length() + " - " + string.toUpperCase());
    }

    public String thenCombineExample() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier);

        String helloWorld = helloCF.thenCombine(worldCF, (helloString, worldString) -> helloString + worldString)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCalls() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });

        String helloWorld = helloCF.thenCombine(worldCF, (helloString, worldString) -> helloString + worldString)
                .thenCombine(hiCF, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCallsLog() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });

        String helloWorld = helloCF.thenCombine(worldCF, (helloString, worldString) -> {
                    log("Inside helloCF thenCombine");
                    return helloString + worldString;
                })
                .thenCombine(hiCF, (previous, current) -> {
                    log("Inside hiCF thenCombine");
                    return previous + current;
                })
                .thenApply(string -> {
                    log("Inside thenApply");
                    return string.toUpperCase();
                })
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCallsLogCustomThreadPool() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier, executorService);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier, executorService);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        }, executorService);

        String helloWorld = helloCF.thenCombine(worldCF, (helloString, worldString) -> {
                    log("Inside helloCF thenCombine");
                    return helloString + worldString;
                })
                .thenCombine(hiCF, (previous, current) -> {
                    log("Inside hiCF thenCombine");
                    return previous + current;
                })
                .thenApply(string -> {
                    log("Inside thenApply");
                    return string.toUpperCase();
                })
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCallsLogAsync() {
        // does not guarantee every task will run in a new Thread. New Thread will only be picked if all currently working threads are blocked.
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });

        String helloWorld = helloCF.thenCombineAsync(worldCF, (helloString, worldString) -> {
                    log("Inside helloCF thenCombine");
                    return helloString + worldString;
                })
                .thenCombineAsync(hiCF, (previous, current) -> {
                    log("Inside hiCF thenCombine");
                    return previous + current;
                })
                .thenApplyAsync(string -> {
                    log("Inside thenApply");
                    return string.toUpperCase();
                })
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithThreeAsyncCallsLogAsyncCustomThreadPool() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier, executorService);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier, executorService);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        }, executorService);

        String helloWorld = helloCF.thenCombineAsync(worldCF, (helloString, worldString) -> {
                    log("Inside helloCF thenCombine");
                    return helloString + worldString;
                }, executorService)
                .thenCombineAsync(hiCF, (previous, current) -> {
                    log("Inside hiCF thenCombine");
                    return previous + current;
                }, executorService)
                .thenApplyAsync(string -> {
                    log("Inside thenApply");
                    return string.toUpperCase();
                }, executorService)
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public String thenCombineExampleWithFourAsyncCalls() {
        startTimer();
        CompletableFuture<String> helloCF = CompletableFuture.supplyAsync(helloSupplier);
        CompletableFuture<String> worldCF = CompletableFuture.supplyAsync(worldSupplier);
        CompletableFuture<String> hiCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Hi Completable Future!";
        });
        CompletableFuture<String> udemyCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " This course is great!";
        });

        String helloWorld = helloCF.thenCombine(worldCF, (helloString, worldString) -> helloString + worldString)
                .thenCombine(hiCF, (previous, current) -> previous + current)
                .thenCombine(udemyCF, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();
        stopWatchReset();
        return helloWorld;
    }

    public CompletableFuture<String> helloWorldThenCompose() {
        log("inside helloWorldThenCompose method");
        return CompletableFuture.supplyAsync(helloSupplier)
                .thenCompose(helloWorldService::worldFuture)
                // thenCompose is a dependent task. It waits for the hello task to complete and because of this the same thread that executes the hello task also executes the world task
                .thenApply(String::toUpperCase); // thenApply is also a dependent task. thenCombine is not
    }

    public String anyOf() {
        CompletableFuture<String> dbCF = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("Response from DB");
            return "hello world";
        });

        CompletableFuture<String> restCallCF = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("Response from REST Call");
            return "hello world";
        });

        CompletableFuture<String> soapCallCF = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("Response from SOAP Call");
            return "hello world";
        });

        List<CompletableFuture<String>> cfList = List.of(dbCF, restCallCF, soapCallCF);
        var cfArray = cfList.toArray(new CompletableFuture[0]);
        CompletableFuture<Object> cfAnyOf = CompletableFuture.anyOf(cfArray);

        return (String) cfAnyOf.thenApply(object -> {
            if (object instanceof String) return object; // if condition because cfAnyOf is a CF of type Object
            return null;
        }).join();
    }
}
