package com.Animesh.Java.FunctionalProgramming.StreamsTerminal;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class StreamsMinByMaxByExample {
    public static Optional<Student> minBy() {
        return StudentDataBase.getAllStudents().stream()
                .collect(Collectors.minBy(Comparator.comparing(Student::getGpa)));
    }

    public static Optional<Student> maxBy() {
        return StudentDataBase.getAllStudents().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));
    }

    public static List<Student> maxByMultipleStudents() {
        List<Student> maxStudents = new ArrayList<>();
        Optional<Student> studentOptional = StudentDataBase.getAllStudents().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Student::getGpa)));

        Student maxStudent = studentOptional.orElse(null);
        if (maxStudent != null) {
            maxStudents = StudentDataBase.getAllStudents().stream()
                    .filter(student -> maxStudent.getGpa() == student.getGpa())
                    .collect(toList());
        }
        return maxStudents;
    }


    public static void main(String[] args) {
        System.out.println(minBy());
        System.out.println(maxBy());
        System.out.println("Max Students are: " + maxByMultipleStudents());
    }
}
