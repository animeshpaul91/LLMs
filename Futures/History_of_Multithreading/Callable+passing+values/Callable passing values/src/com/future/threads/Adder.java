package com.future.threads;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;


public class Adder implements Callable<Integer> {
    private final int number1;
    private final int number2;

    public Adder(int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    @Override
    public Integer call() throws InterruptedException {
        sleep(500);
        int sum = 0;
        for (int num = number1; num <= number2; num++) {
            sum += num;
        }
        return sum;
    }
}