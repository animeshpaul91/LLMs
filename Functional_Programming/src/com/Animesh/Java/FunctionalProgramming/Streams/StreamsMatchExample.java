package com.Animesh.Java.FunctionalProgramming.Streams;

import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

public class StreamsMatchExample {
    public static boolean allMatch() {
        return StudentDataBase.getAllStudents().stream()
                .allMatch(student -> student.getGpa() >= 3.9); // all elements have to meet this condition
    }

    public static boolean anyMatch() {
        return StudentDataBase.getAllStudents().stream()
                .anyMatch(student -> student.getGpa() >= 3.9); // at least one element has to meet this condition
    }

    public static boolean noneMatch() {
        return StudentDataBase.getAllStudents().stream()
                .noneMatch(student -> student.getGpa() >= 3.9); // all elements have to violate this condition
    }

    public static void main(String[] args) {
        System.out.println("Result of allMatch : " + allMatch());
        System.out.println("Result of anyMatch : " + anyMatch());
        System.out.println("Result of noneMatch : " + noneMatch());
    }
}
