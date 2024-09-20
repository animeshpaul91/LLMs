package com.Animesh.Java.FunctionalProgramming.Lamdas;

public class RunnableLambdasImpl {
    public static void main(String[] args) {
        // Implementing Functional Interfaces (Interfaces with only 1 abstract method)

        // The Traditional Way (Before Java - 8)
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("This is a new Thread - 1");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        // The Lambda Way (Replacing Anonymous Classes)
        Runnable runnable1 = () -> System.out.println("This is new Thread - 2");
        Thread thread1 = new Thread(runnable1);
        thread1.start();

        // Create anonymous thread on 1 line
        new Thread(() -> System.out.println("This is new Thread - 3")).start();
    }
}
