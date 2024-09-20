package com.future.threads;

import java.time.Instant;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class ThreadNamePrinter implements Runnable{
    public ThreadNamePrinter (){
    }
    @Override
    public void run(){
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" Job completed successfully : Name of the thread is" + currentThread().getName());
        Instant end = Instant.now();
        System.out.println("End time " + end);
    }

}