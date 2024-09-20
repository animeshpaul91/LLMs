package com.modernjava.var;

import java.util.List;
import java.util.Map;


public class VarTypeExample {
    //var in the class properties are not allowed.
    //private var x = "abc";

    public static void main(String[] args) {
        final var list = List.of("Animesh", "Paul");
        for (var name : list) {
            final var transformedName = transform(name);
            System.out.println("transformedName = " + transformedName);
        }

        final var map = Map.ofEntries(Map.entry("a", list));
        map.forEach((key, names) -> System.out.println("key = " + key));

        // Limitations
        // var x = null;
    }

    static String transform(String name) { // var in the function argument is not allowed
        return name.toUpperCase();
    }
}
