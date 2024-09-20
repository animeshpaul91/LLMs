package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BiPredicateExample {

    private static final BiPredicate<Integer, Double> biPredicate = (gradeLevel, gpa) -> gradeLevel >= 3 && gpa >= 3.9;

    private static final Consumer<Student> consumer = (student) -> {
        if (biPredicate.test(student.getGradeLevel(), student.getGpa())) {
            System.out.println(student.getName() + " " + student.getActivities());
        }
    };

    public static void filterStudents() {
        List<Student> studentList = StudentDataBase.getAllStudents();
        studentList.forEach(consumer);
    }

    public static void main(String[] args) {
        filterStudents();
    }

}
