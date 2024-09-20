package com.Animesh.Java.FunctionalProgramming.MethodReferences;

import com.Animesh.Java.FunctionalProgramming.data.Student;

import java.util.function.Function;
import java.util.function.Supplier;

public class ConstructorReferenceExample {
    private static final Supplier<Student> studentSupplier = Student::new;
    private static final Function<String, Student> studentFunction = Student::new;

    // Student student = Student::new; // This will result in a compilation error

    public static void main(String[] args) {
        System.out.println(studentSupplier.get());
        System.out.println(studentFunction.apply("Client123"));
    }
}
