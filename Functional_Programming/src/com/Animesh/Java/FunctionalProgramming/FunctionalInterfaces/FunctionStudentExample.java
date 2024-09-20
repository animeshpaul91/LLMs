package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FunctionStudentExample {
    private final static Function<List<Student>, Map<String, Double>> function = (students -> {
        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach(student -> {
            if (PredicateStudentExample.p1.test(student)) {
                studentGradeMap.put(student.getName(), student.getGpa());
            }
        });
        return studentGradeMap;
    });

    public static void main(String[] args) {
        List<Student> allStudents = StudentDataBase.getAllStudents();
        Map<String, Double> studentGradeMap = function.apply(allStudents);
        System.out.println(studentGradeMap);
    }
}
