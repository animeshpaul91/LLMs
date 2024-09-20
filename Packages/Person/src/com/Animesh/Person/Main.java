package com.Animesh.Person;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Person obj1 = new Person("Animesh", 27);
        Person obj2 = new Person("Animesh", 28);
        Person obj3 = new Person("Sandeep", 28);
        // System.out.println(obj1.getClass().getSimpleName());
        // System.out.println(obj2.getClass().getSimpleName());
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
        System.out.println(obj3.hashCode());
        System.out.println(obj1.equals(obj2));
        System.out.println(obj1.hashCode() == obj2.hashCode());
        // System.out.println(obj1 instanceof Object);
        // System.out.println(obj1 instanceof Person);
        Set<Person> set = new HashSet<>();
        set.add(obj1);
        set.add(obj2);
        set.add(obj3);
        System.out.println(set.size());
    }
}
