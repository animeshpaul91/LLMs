package com.Animesh.Series;

public class Main {

    public static void main(String[] args) {
	// write your code here
        for(int i = 0; i < 11; i++)
            System.out.println(Series.nsum(i));
        for(int i = 0; i < 11; i++)
            System.out.println(Series.factorial(i));
        for(int i = 0; i < 11; i++)
            System.out.println(Series.fibonacci(i));
    }
}
