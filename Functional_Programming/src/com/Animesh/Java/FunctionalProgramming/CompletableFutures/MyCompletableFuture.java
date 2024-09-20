package com.Animesh.Java.FunctionalProgramming.CompletableFutures;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

public class MyCompletableFuture {
    public static void main(String[] args) {
        testCF("Animesh");
    }

    private static CompletionStage<Boolean> testCF(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("This is a simple log line");
            return completedStage(false);
        }

        test();
        return completedStage(true);
    }

    private static void test() {
        System.out.println("This is a another log line");
    }
}
