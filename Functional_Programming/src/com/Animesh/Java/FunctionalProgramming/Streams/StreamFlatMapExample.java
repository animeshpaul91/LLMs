package com.Animesh.Java.FunctionalProgramming.Streams;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamFlatMapExample {

    public static List<String> printStudentActivities() {
        return StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) //<Stream<String>
                .collect(toList());

    }

    public static List<String> printUniqueStudentActivities() {
        return StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .collect(toList());

    }

    public static long getStudentActivitiesCount() {
        return StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities)
                .flatMap(List::stream)
                .distinct()
                .count();
    }

    public static void main(String[] args) {
        System.out.println("Student Activities : " + printStudentActivities());
        System.out.println("Unique Student Activities : " + printUniqueStudentActivities());
        System.out.println("Unique Student Activities Count: " + getStudentActivitiesCount());
    }
}
