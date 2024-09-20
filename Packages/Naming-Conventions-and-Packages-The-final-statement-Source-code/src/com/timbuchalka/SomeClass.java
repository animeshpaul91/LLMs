package com.timbuchalka;

/**
 * Created by dev on 20/11/2015.
 */
public class SomeClass {

    private static int classCounter = 0;
    public final int instanceNumber;


    public SomeClass(String name) {
        instanceNumber = ++classCounter;
        System.out.println(name + " created, instance is " + instanceNumber);
    }

    public int getInstanceNumber() {
        return instanceNumber;
    }
}
