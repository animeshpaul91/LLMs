package com.Animesh.Java.FunctionalProgramming.ParallelStreams;

public class Sum {
    private int total;

    public Sum(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void performSum(int input) {
        total += input; // Race Condition Occurs here when multiple threads concurrently access shared variable
    }
}
