package com.Animesh.Java.FunctionalProgramming.ParallelStreams;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ParallelStreamPipelineExample {
    public static List<String> sequentialStream() {
        long start = System.currentTimeMillis();
        List<String> studentActivities = StudentDataBase.getAllStudents()
                .stream()
                .map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) //<Stream<String>
                .collect(toList());

        long duration = System.currentTimeMillis() - start;
        System.out.println("Duration in sequential stream : " + duration);
        return studentActivities;
    }

    public static List<String> parallelStream() {
        long start = System.currentTimeMillis();
        List<String> studentActivities = StudentDataBase.getAllStudents()
                .parallelStream()
                .map(Student::getActivities) //Stream<List<String>>
                .flatMap(List::stream) //<Stream<String>
                .collect(toList());

        long duration = System.currentTimeMillis() - start;
        System.out.println("Duration in parallel stream : " + duration);
        return studentActivities;
    }


    public static void main(String[] args) {
        System.out.println("sequentialStream : " + sequentialStream());
        System.out.println("parallelStream : " + parallelStream());
    }
}
