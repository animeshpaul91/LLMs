package com.future.threads;

public class ThreadExample {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start(); // this will start a new thread
        for (int i = 0; i<20;i++) {
            System.out.println("Main running"); // Main thread runs this
        }
    }
}

