package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SupplierExample {

    public static Supplier<Student> studentSupplier = () -> new Student("Adam", 2, 4.0, "male", Arrays.asList("swimming", "basketball", "volleyball"));
    public static Supplier<List<Student>> studentsSupplier = () -> StudentDataBase.getAllStudents();

    public static void main(String[] args) {
        Student student = studentSupplier.get();
        System.out.println("Student is : " + student);
        System.out.println("Students are : " + studentsSupplier.get());
    }
}
