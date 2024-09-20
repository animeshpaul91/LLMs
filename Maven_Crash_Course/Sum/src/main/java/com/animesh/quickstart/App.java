package com.animesh.quickstart;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Throwable{
        if (args.length == 0) throw new RuntimeException("No Arguments Passed");
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int c = a + b;
        System.out.println("Sum is: " + c);
    }
}
