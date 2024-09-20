package com.Animesh.Java.FunctionalProgramming.Defaults;

import com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces.Interface1;
import com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces.Interface4;

public class Client14 implements Interface1, Interface4 {

    /* You need to override the method in a class if the class implements two interfaces having methods of the same name and signature, provided
       those interfaces do not have any direct hierarchical relationship in terms of inheritance.
     */

    public void methodA() {
        System.out.println("Inside overridden method " + Client14.class);
    }

    public static void main(String[] args) {
        Client14 client14 = new Client14();
        client14.methodA();
    }
}
