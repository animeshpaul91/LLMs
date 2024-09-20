package com.Animesh.Java.FunctionalProgramming.Streams;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class StreamsMapExample {
    private static List<String> namesUpperCase(List<Student> names) {
        return names.stream() //Stream<Student>
                .map(Student::getName) //Stream<String>
                .map(String::toUpperCase) // Stream<String> -> UpperCase
                .collect(toList());
    }

    private static Set<String> namesSetUpperCase(List<Student> students) {
        return students.stream() //Stream<Student>
                .map(Student::getName) //Stream<String>
                .collect(toSet());
    }

    private static Map<String, Integer> namesLengthMap(ArrayList<String> names) {
        return names.stream() // Stream<String>
                .collect(toMap(String::toString, String::length));
    }


    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("adam");
        names.add("dan");
        names.add("jenny");

        System.out.println("namesUpperCase List : " + namesUpperCase(StudentDataBase.getAllStudents()));
        System.out.println("namesLowerCase Set : " + namesSetUpperCase(StudentDataBase.getAllStudents()));
        System.out.println("namesLengthMap : " + namesLengthMap(names));
    }
}
