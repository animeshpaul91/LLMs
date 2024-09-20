package com.Animesh.Iterators;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        List<String> strings = new ArrayList<>();
        strings.add("Animesh Paul");
        strings.add("Sandeep Gohain");
        strings.add("Swagat Bhattacharjee");
        strings.add("Praveen Chhetri");
        strings.add("Boris Laishram");

        ListIterator<String> litr = strings.listIterator();

        while (litr.hasNext()) {
            String name = litr.next();
            litr.set(name + " +");
        }

        while (litr.hasPrevious()) {
            String name = litr.previous();
            System.out.println(name);
        }
    }
}
