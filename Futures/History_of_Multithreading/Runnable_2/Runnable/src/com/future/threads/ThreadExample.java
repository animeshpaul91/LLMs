package com.future.threads;

public class ThreadExample {
    public static void main(String[] args) {
        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        for (int i = 0; i < 20; i++) {
            System.out.println("Main running");
        }
        System.out.println("Main Thread Id: " + Thread.currentThread().getId());
    }
}

