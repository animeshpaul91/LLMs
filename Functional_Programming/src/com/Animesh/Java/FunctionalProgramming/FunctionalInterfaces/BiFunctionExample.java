package com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class BiFunctionExample {
    private static final Map<String, String> loginPageLocs = new HashMap<>();
    private static final BiFunction<List<Student>, Predicate<Student>, Map<String, Double>> biFunction = (students, studentPredicate) -> {
        Map<String, Double> studentGradeMap = new HashMap<>();
        students.forEach((student -> {
            if (studentPredicate.test(student)) {
                studentGradeMap.put(student.getName(), student.getGpa());
            }
        }));
        return studentGradeMap;
    };

    public static String getLoginLocs(String sLocator, String elementType) {
        return loginPageLocs.get(elementType);
    }

    private final static BiFunction<String, String, String> getLoginLocs = (sLocator, elementType) -> loginPageLocs.get(sLocator);

    public static void main(String[] args) {
        List<Student> allStudents = StudentDataBase.getAllStudents();
        Map<String, Double> studentGradeMap = biFunction.apply(allStudents, PredicateStudentExample.p2);
        System.out.println(studentGradeMap);
        getLoginLocs.apply("locator", "elementType");
    }
}
