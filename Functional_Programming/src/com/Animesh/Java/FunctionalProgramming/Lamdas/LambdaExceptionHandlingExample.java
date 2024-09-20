package com.Animesh.Java.FunctionalProgramming.Lamdas;

import com.Animesh.Java.FunctionalProgramming.data.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LambdaExceptionHandlingExample {
    private static final Consumer<Student> studentConsumer = (s) -> { // raises exception due to uninitialized student
        try {
            System.out.println(s.getName().toUpperCase());
        } catch (Exception e) {
            System.err.println("Exception is studentConsumer : " + e);
        }
    };

    private static Consumer<Student> printNameInUpperCase(Consumer<Student> consumer) { // raises exception due to uninitialized student
        return student -> {
            try {
                consumer.accept(student);
            } catch (Exception e) {
                System.err.println("Exception is printNameInUpperCase : " + e);
            }
        };
    }

    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        studentList.forEach(studentConsumer);
        studentList.forEach(printNameInUpperCase((student) -> System.out.println(student.getName().toUpperCase())));
    }
}
