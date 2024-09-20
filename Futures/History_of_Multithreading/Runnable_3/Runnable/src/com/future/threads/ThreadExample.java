package com.future.threads;


public class ThreadExample {
    public static void main(String[] args) throws InterruptedException {
        //Runnable Implemented as Anonymous inner class
        Runnable runnable = () -> {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("MyRunnable running");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(10);
            System.out.println("Main running");
        }
    }
}