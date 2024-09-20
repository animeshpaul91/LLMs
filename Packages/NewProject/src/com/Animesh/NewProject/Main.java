package com.Animesh.NewProject;

import com.Animesh.Series.Series;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int i;
        for(i = 0; i < 11; ++i) {
            System.out.println(Series.nsum(i));
        }

        for(i = 0; i < 11; ++i) {
            System.out.println(Series.factorial(i));
        }

        for(i = 0; i < 11; ++i) {
            System.out.println(Series.fibonacci(i));
        }
    }
}
