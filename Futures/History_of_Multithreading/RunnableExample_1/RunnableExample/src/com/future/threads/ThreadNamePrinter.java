package com.future.threads;

import java.time.Instant;
import java.util.concurrent.*;

import static java.lang.Thread.*;


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
        String name = currentThread().getName();
        System.out.println(" Job completed successfully : Name of the thread is: " + name);
        Instant end = Instant.now();
        System.out.println(name + " End time: " + end);
    }

}