package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;
import java.util.function.BiConsumer;

public class BiConsumerExample {

    private static final List<Student> students = StudentDataBase.getAllStudents();

    private static void printNamesAndActivities() {
        BiConsumer<String, List<String>> biConsumer = (name, activities) -> System.out.println(name + " " + activities);
        students.forEach((student) -> biConsumer.accept(student.getName(), student.getActivities()));
    }

    public static void main(String[] args) {
        BiConsumer<String, String> c1 = (s1, s2) -> System.out.println(s1.concat(" ").concat(s2));
        c1.accept("Animesh", "Paul");
        BiConsumer<Integer, Integer> c2 = (a, b) -> System.out.print("Product: " + a * b + " ");
        BiConsumer<Integer, Integer> c3 = (a, b) -> System.out.println("Quotient: " + a / b + " ");
        c2.andThen(c3).accept(3, 2);
        printNamesAndActivities();
    }
}
