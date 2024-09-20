package com.timbuchalka;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        Comparator<Integer> comp = (a, b) -> {
            if (a < b)
                return 1;
            else if (a > b)
                return -1;
            return 0;
        };

        PriorityQueue<Integer> queue = new PriorityQueue<>(comp);

        queue.add(5);
        queue.add(1);
        queue.add(-1);
        queue.add(10);
        System.out.println(queue.peek());
    }
}
