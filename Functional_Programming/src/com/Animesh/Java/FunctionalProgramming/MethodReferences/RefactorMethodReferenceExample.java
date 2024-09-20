package com.Animesh.Java.FunctionalProgramming.MethodReferences;

import com.Animesh.Java.FunctionalProgramming.FunctionalInterfaces.SupplierExample;
import com.Animesh.Java.FunctionalProgramming.data.Student;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class RefactorMethodReferenceExample {
    private final static Predicate<Student> predicateUsingLambda = (s) -> s.getGradeLevel() >= 3;
    private final static Predicate<Student> predicateUsingMetRef = RefactorMethodReferenceExample::greaterThan;
    private final static BiPredicate<Student, Integer> predicateUsingMethodReference = RefactorMethodReferenceExample::greaterThan;

    private static boolean greaterThan(Student student) {
        return student.getGradeLevel() > 3;
    }

    private static boolean greaterThan(Student student, Integer grade) {
        return student.getGradeLevel() > grade;
    }

    public static void main(String[] args) {
        Student student = SupplierExample.studentSupplier.get();
        System.out.println(predicateUsingLambda.test(student));
        System.out.println(predicateUsingMetRef.test(student));
        System.out.println(predicateUsingMethodReference.test(student, 3));
    }
}
