package com.learnjava.completablefutures;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletableFutureExampleTest {
    private final CompletableFutureExample completableFutureExample;

    public CompletableFutureExampleTest() {
        this.completableFutureExample = new CompletableFutureExample();
    }

    @Test
    public void testHelloWorldCF() {
        CompletableFuture<String> cfh = completableFutureExample.helloWorld();
        cfh.thenAccept(string -> { // without join, the main thread won't even go inside the lambda block
            log("Thread still executes this block of code");
            assertEquals(string.toUpperCase(), string);
            assertEquals("HELLO", string);
        }).join();
    }

    @Test
    public void testHelloWorldWithSizeCF() {
        CompletableFuture<String> cfh = completableFutureExample.helloWorldWithSize();
        cfh.thenAccept(string -> { // without join, the main thread won't even go inside the lambda block
            log("Thread still executes this block of code");
            assertEquals("5 - HELLO", string);
        }).join();
    }

    @Test
    void thenCombineExample() {
        String helloWorld = completableFutureExample.thenCombineExample();
        assertEquals("HELLO WORLD!", helloWorld);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCalls() {
        String helloWorldThreeAsyncCalls = completableFutureExample.thenCombineExampleWithThreeAsyncCalls();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", helloWorldThreeAsyncCalls);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsLog() {
        String helloWorldThreeAsyncCalls = completableFutureExample.thenCombineExampleWithThreeAsyncCallsLog();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", helloWorldThreeAsyncCalls);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsLogCustomThreadPool() {
        String helloWorldThreeAsyncCalls = completableFutureExample.thenCombineExampleWithThreeAsyncCallsLogCustomThreadPool();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", helloWorldThreeAsyncCalls);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsLogAsync() {
        String helloWorldThreeAsyncCalls = completableFutureExample.thenCombineExampleWithThreeAsyncCallsLogAsync();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", helloWorldThreeAsyncCalls);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsLogAsyncCustomThreadPool() {
        String helloWorldThreeAsyncCalls = completableFutureExample.thenCombineExampleWithThreeAsyncCallsLogAsyncCustomThreadPool();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", helloWorldThreeAsyncCalls);
    }

    @Test
    void thenCombineExampleWithFourAsyncCalls() {
        String helloWorldFourAsyncCalls = completableFutureExample.thenCombineExampleWithFourAsyncCalls();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE! THIS COURSE IS GREAT!", helloWorldFourAsyncCalls);
    }

    @Test
    void helloWorldThenCompose() {
        startTimer();
        CompletableFuture<String> cfh = completableFutureExample.helloWorldThenCompose();
        cfh.thenAccept(helloWorldString -> assertEquals("HELLO WORLD!", helloWorldString)).join();
        timeTaken();
        stopWatchReset();
    }

    @Test
    void testAnyOf() {
        String helloWorld = completableFutureExample.anyOf();
        assertEquals("hello world", helloWorld);
    }
}