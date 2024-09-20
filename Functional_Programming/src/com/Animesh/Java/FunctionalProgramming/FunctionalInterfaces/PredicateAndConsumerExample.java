package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PredicateAndConsumerExample {
    private static final Predicate<Student> p1 = (s) -> s.getGradeLevel() >= 3;
    private static final Predicate<Student> p2 = (s) -> s.getGpa() >= 3.9;
    private static final BiConsumer<String, List<String>> studentBiConsumer = (name, activities) -> System.out.println(name + " : " + activities);

    private static final Consumer<Student> studentConsumer = (student) -> {
        if (p1.and(p2).test(student)) { // using predicates as filter expression
            studentBiConsumer.accept(student.getName(), student.getActivities());
        }
    };

    private static void printNameAndActivities(List<Student> studentList) {
        studentList.forEach(studentConsumer);
    }

    public static void main(String[] args) {
        List<Student> studentList = StudentDataBase.getAllStudents();
        printNameAndActivities(studentList);
    }
}
