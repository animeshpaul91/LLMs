package com.Animesh.Java.FunctionalProgramming.Optional;


import com.Animesh.Java.FunctionalProgramming.data.Student;

import java.util.Optional;

public class OptionalOrElseExample {

    public static String optionalOrElse() {
        //Optional<Student> getOptionalStudent = Optional.ofNullable(StudentDataBase.studentSupplier.get());
        Optional<Student> optionalStudent = Optional.ofNullable(null);
        return optionalStudent.map(Student::getName).orElse("Default");
        // chaining with orElse() causes a string to be returned in either case

    }

    public static String optionalOrElseGet() {
        //Optional<Student> getOptionalStudent = Optional.ofNullable(StudentDataBase.studentSupplier.get());
        Optional<Student> optionalStudent = Optional.ofNullable(null);
        return optionalStudent.map(Student::getName).orElseGet(() -> "Default");
    }

    public static String optionalOrElseThrow() {
        //Optional<Student> getOptionalStudent = Optional.ofNullable(StudentDataBase.studentSupplier.get());
        Optional<Student> optionalStudent = Optional.ofNullable(null);
        return optionalStudent.map(Student::getName).orElseThrow(() -> new RuntimeException("No Data available"));
    }

    public static void main(String[] args) {
        System.out.println(optionalOrElse());
        System.out.println(optionalOrElseGet());
        System.out.println(optionalOrElseThrow());
    }
}
