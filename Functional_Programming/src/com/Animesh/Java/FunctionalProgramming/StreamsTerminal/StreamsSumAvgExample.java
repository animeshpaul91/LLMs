package com.Animesh.Java.FunctionalProgramming.StreamsTerminal;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBaseWithNotebooks;

import java.util.stream.Collectors;

public class StreamsSumAvgExample {
    public static int sum() {
        return StudentDataBaseWithNotebooks.getAllStudents()
                .stream()
                .collect(Collectors.summingInt(Student::getNoteBooks));
    }

    public static double average() {
        return StudentDataBaseWithNotebooks.getAllStudents()
                .stream()
                .collect(Collectors.averagingInt(Student::getNoteBooks));
    }


    public static void main(String[] args) {
        System.out.println("Total no of Notebooks : " + sum());
        System.out.println("Average no of Notebooks : " + average());
    }
}
