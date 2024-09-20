package com.Animesh.Java.FunctionalProgramming.Defaults;

import com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces.Interface1;
import com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces.Interface2;
import com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces.Interface3;

public class Client123 implements Interface1, Interface2, Interface3 {
    /*
        Order is:
        1. The overridden method in the Class is called if the class overrides any interface method
        2. The default method in the interface - IB gets called if a class implements 2 interfaces - IA and IB where IB extends IA
           and IB overrides a default method of IA.
     */

    @Override
    public void methodA() { //overriding the default method in the implementation class.
        System.out.println("Inside method A " + Client123.class);
    }

    public static void main(String[] args) {
        Client123 client123 = new Client123();
        client123.methodA(); // resolves to child Interface Implementation
        client123.methodB();
        client123.methodC();
    }
}
