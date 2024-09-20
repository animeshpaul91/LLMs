package com.Animesh.Comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PointMain {

    public static void main(String[] args) {
        OrderedPoint p1 = new OrderedPoint(-1, 1);
        OrderedPoint p2 = new OrderedPoint(-1, -1);
        OrderedPoint p3 = new OrderedPoint(1, -1);
        OrderedPoint p4 = new OrderedPoint(1, 1);

        Comparator<OrderedPoint> myComp = (point1, point2) -> {
            int compare = Integer.compare(point1.getX(), point2.getX());
            if (compare != 0) return compare;
            return Integer.compare(point1.getY(), point2.getY());
        };

        List<OrderedPoint> list = new ArrayList<>();
        list.add(p4);
        list.add(p3);
        list.add(p2);
        list.add(p1);

        list.sort(myComp);

        for (OrderedPoint orderedPoint : list)
            System.out.println(orderedPoint);

        int index = Collections.binarySearch(list, p4, myComp);
        System.out.println("The searched index was found at: " + index);
    }
}
