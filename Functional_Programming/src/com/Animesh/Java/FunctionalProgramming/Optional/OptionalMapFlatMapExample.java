package com.Animesh.Java.FunctionalProgramming.Optional;


import com.Animesh.Java.FunctionalProgramming.data.Bike;
import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.Optional;

public class OptionalMapFlatMapExample {

    public static void optionalFlatMap() {
        Optional<Student> studentOptional = StudentDataBase.getOptionalStudent();
        Optional<String> name = studentOptional // Optional<Student>
                .filter(student -> student.getGpa() >= 3.5) // Optional<Student>
                .flatMap(Student::getBike) // Optional<Bike>
                .map(Bike::getName); // Optional<String>
        name.ifPresent(System.out::println);
    }

    public static void optionalMap() {
        Optional<Student> studentOptional = StudentDataBase.getOptionalStudent();
        if (studentOptional.isPresent()) {
            Optional<String> nameOptional = studentOptional.
                    map(Student::getName); //
            nameOptional.ifPresent(System.out::println);
        }
    }

    public static void optionalFilter() {
        Optional<Student> studentOptional = StudentDataBase.getOptionalStudent()
                .filter(student -> student.getGpa() >= 3.5);
        studentOptional.ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        optionalFlatMap();
        optionalMap();
        optionalFilter();
    }
}
