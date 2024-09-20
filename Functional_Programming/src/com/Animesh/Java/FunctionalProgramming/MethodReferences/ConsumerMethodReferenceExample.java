package com.Animesh.Java.FunctionalProgramming.MethodReferences;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.function.Consumer;

public class ConsumerMethodReferenceExample {

    /**
     * Class::instancemethod
     */
    private final static Consumer<Student> c1 = System.out::println;

    /**
     * instance::instancemethod
     */
    private final static Consumer<Student> c2 = (student) -> student.printListOfActivities();
    private final static Consumer<Student> c3 = Student::printListOfActivities;

    public static void main(String[] args) {
        StudentDataBase.getAllStudents().forEach(c1);
        // StudentDataBase.getAllStudents().forEach(c2);
        StudentDataBase.getAllStudents().forEach(c3);
    }
}
