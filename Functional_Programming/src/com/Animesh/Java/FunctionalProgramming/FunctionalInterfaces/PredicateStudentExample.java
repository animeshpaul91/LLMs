package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;
import java.util.function.Predicate;

public class PredicateStudentExample {
    public static final Predicate<Student> p1 = (s) -> s.getGradeLevel() >= 3;
    public static final Predicate<Student> p2 = (s) -> s.getGpa() >= 3.9;
    public static final List<Student> studentList = StudentDataBase.getAllStudents();

    private static void filterStudentsAnd() {
        System.out.println("Filter Students And: ");
        studentList.forEach((student) -> {
            if (p1.and(p2).test(student))
                System.out.println(student);
        });
    }

    private static void filterStudentsOr() {
        System.out.println("Filter Students Or: ");
        studentList.forEach((student) -> {
            if (p1.or(p2).negate().test(student))
                System.out.println(student);
        });
    }

    public static void main(String[] args) {
        filterStudentsAnd();
        filterStudentsOr();
    }
}
