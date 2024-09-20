package com.Animesh.Java.FunctionalProgramming.StreamsTerminal;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBaseWithNotebooks;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamsGroupingByExample {
    public static void groupingByGender() {
        Map<String, List<Student>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(Student::getGender));

        Stream.of(studentMap).forEach(System.out::println);
    }

    public static void customizedGroupingBy() {
        Map<String, List<Student>> studentMap = StudentDataBase.getAllStudents()
                .stream()
                .collect(Collectors.groupingBy(student -> student.getGpa() >= 3.8 ? "OUTSTANDING" : "AVERAGE"));

        Stream.of(studentMap).forEach(System.out::println);
    }

    /**
     * Grouping by Two parameters
     */
    public static void twoLevelGrouping() {
        Map<Integer, Map<String, List<Student>>> studentMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        groupingBy(student -> student.getGpa() >= 3.8 ? "Outstanding" : "Average")));

        Stream.of(studentMap).forEach(System.out::println);
    }

    /**
     * Grouping by Two parameters
     */
    public static void twoLevelGrouping_2() {
        Map<String, Integer> nameNoteBooksMap = StudentDataBaseWithNotebooks.getAllStudents().stream()
                .collect(groupingBy(Student::getName,
                        summingInt(Student::getNoteBooks)));// second argument can be of any type of collector
        // return a map of student name and sum of notebooks that student has
        System.out.println(nameNoteBooksMap);
    }

    /**
     * Grouping by Two parameters
     */
    public static void twoLevelGrouping_3() {
        Map<String, Set<Student>> nameNoteBooksMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getName,
                        toSet()));// second argument can be of any type of collector

        System.out.println(nameNoteBooksMap);
    }


    public static void threeArgumentGroupingBy() {
        LinkedHashMap<String, Set<Student>> studentMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getName, LinkedHashMap::new,
                        toSet()));

        Stream.of(studentMap).forEach(System.out::println);
    }


    public static void calculateTopGPAStudentInEachGrade() {
        Map<Integer, Optional<Student>> studentMapOptional = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel, maxBy(Comparator.comparingDouble(Student::getGpa))
                ));

        // Stream.of(studentMapOptional).forEach(System.out::println);
        Map<Integer, Student> studentMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(maxBy(Comparator.comparingDouble(Student::getGpa)), Optional::get)));

        Stream.of(studentMap).forEach(System.out::println);
    }

    public static void calculateLeastGPAStudentInEachGrade() {
        Map<Integer, Optional<Student>> studentMapOptional = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel, minBy(Comparator.comparingDouble(Student::getGpa))
                ));

        // Stream.of(studentMapOptional).forEach(System.out::println);
        Map<Integer, Student> studentMap = StudentDataBase.getAllStudents().stream()
                .collect(groupingBy(Student::getGradeLevel,
                        collectingAndThen(minBy(Comparator.comparingDouble(Student::getGpa)), Optional::get)));

        Stream.of(studentMap).forEach(System.out::println);
    }

    public static void main(String[] args) {
        groupingByGender();
        customizedGroupingBy();
        twoLevelGrouping();
        twoLevelGrouping_2();
        twoLevelGrouping_3();
        threeArgumentGroupingBy();
        calculateTopGPAStudentInEachGrade();
        calculateLeastGPAStudentInEachGrade();
        threeArgumentGroupingBy();
    }
}
