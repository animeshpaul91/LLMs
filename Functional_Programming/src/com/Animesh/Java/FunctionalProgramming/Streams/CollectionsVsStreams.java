package com.Animesh.Java.FunctionalProgramming.Streams;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CollectionsVsStreams {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Adam");
        names.add("Jim");
        names.add("Jenny");

//        for(String name: names)
//            System.out.println(name);
//
//        for(String name: names)
//            System.out.println(name);
        // Collections can be iterated n # of times

        Stream<String> namesStream = names.stream();
        namesStream.forEach(System.out::println);
        //namesStream.forEach(System.out::println); // this will raise an exception because the stream has already been iterated
        // internal iteration
    }
}
