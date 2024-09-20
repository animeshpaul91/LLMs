package com.Animesh.Java.FunctionalProgramming.MethodReferences;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionMethodReferenceExample {

    /**
     * Class::instance method
     *
     * There are 3 types of method references
     * ClassName::instanceMethodName
     * ClassName::staticMethodName
     * Instance::methodName
     */

    private static final Function<String, String> toUpperCaseLambda = String::toUpperCase;
    private static final Function<String, String> toUpperCaseMethodReference = String::toUpperCase;
    private static final Consumer<Student> printName = Student::printName;

    public static void main(String[] args) {
        System.out.println(toUpperCaseLambda.apply("java8"));
        System.out.println(toUpperCaseMethodReference.apply("java8"));
        List<Student> students = StudentDataBase.getAllStudents();
        students.forEach(printName);
    }
}
