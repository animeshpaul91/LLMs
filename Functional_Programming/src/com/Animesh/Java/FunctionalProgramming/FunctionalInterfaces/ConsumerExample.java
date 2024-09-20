package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {
    private static final Consumer<String> c1 = (s) -> System.out.println(s.toUpperCase());
    private static final Consumer<Student> c2 = (s) -> System.out.print(s.getName() + " ");
    private static final Consumer<Student> c3 = (s) -> System.out.println(s.getActivities());
    private static final List<Student> students = StudentDataBase.getAllStudents();

    private static void printNames() {
        students.forEach(c2);
    }

    private static void printNamesAndActivities() {
        students.forEach(c2.andThen(c3)); // Consumer Chaining
    }

    private static void printNamesAndActivitiesUsingCondition() {
        students.forEach((student) -> { // Anonymous Consumer/FI
            if (student.getGradeLevel() > 2 && student.getGpa() > 3.8) // accept gets implicitly called on anonymous consumer/FI
                c2.andThen(c3).accept(student); // accept needs to be explicitly called on c2 and c3
        });
    }

    public static void main(String[] args) {
        c1.accept("java-8");
//        printNames();
//        printNamesAndActivities();
        printNamesAndActivitiesUsingCondition();
    }
}
