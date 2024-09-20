package com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces;

/**
 * Created by z001qgd on 8/2/18.
 */
public interface Interface2 extends Interface1 {

    default void methodB() {
        System.out.println("Inside method B");
    }

    @Override
    default void methodA() {
        System.out.println("Inside method A " + Interface2.class);
    }
}
