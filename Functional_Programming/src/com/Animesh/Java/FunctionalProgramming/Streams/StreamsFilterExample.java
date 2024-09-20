package com.Animesh.Java.FunctionalProgramming.Streams;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamsFilterExample {
    private static List<Student> filterStudents() {
        return StudentDataBase.getAllStudents()
                .stream()
                .filter(student -> student.getGpa() >= 3.9)
                .filter(student -> student.getGender().equals("female"))
                .collect(toList());
    }

    private static List<String> getFemaleStudentNames() {
        return StudentDataBase.getAllStudents().stream()
                .filter(student -> student.getGender().equals("female"))
                .map(Student::getName)
                .collect(toList());
    }

    public static void main(String[] args) {
        System.out.println("Filtered Students : " + filterStudents());
        System.out.println("Names of Females: " + getFemaleStudentNames());
    }
}
