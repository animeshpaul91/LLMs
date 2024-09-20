package com.Animesh.Comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PairMain {
    public static void main(String[] args) {
        Pair p1 = new Pair(-1, 1);
        Pair p2 = new Pair(-1, -1);
        Pair p3 = new Pair(1, -1);
        Pair p4 = new Pair(1, 1);

        List<Pair> list = new ArrayList<>();
        list.add(p4);
        list.add(p3);
        list.add(p2);
        list.add(p1);

        Collections.sort(list);
        for (Pair pair : list)
            System.out.println(pair);

        int index = Collections.binarySearch(list, p4);
        System.out.println("The searched index was found at: " + index);
    }
}
