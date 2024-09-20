package com.Animesh.Java.FunctionalProgramming.Streams;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReduceExample {
    // Reduce is a terminal operation. It reduces a stream to one single value (when a starting value is specified) or Optional<>.
    public static int performMultiplication(List<Integer> integerList) {
        return integerList.stream() // with initial value
                .reduce(1, (a, b) -> a * b);

    }

    public static Optional<Integer> performMultiplicationWithNoInitialValue(List<Integer> integerList) {
        return integerList.stream() // without initial value
                .reduce((a, b) -> a * b); // performs multiplication for each element in the stream and returns a new result fo.
    }

    public static String combineStudentNames() {
        return StudentDataBase.getAllStudents().stream()
                .map(Student::getName)
                .distinct()
                .reduce("", String::concat);  // performs multiplication for each element in the stream.
    }

    public static Optional<Student> getHighestGradeStudent() {
        return StudentDataBase.getAllStudents().stream()
                .reduce((s1, s2) -> (s1.getGpa() > s2.getGpa()) ? s1 : s2);
    }


    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 3, 5, 7);
        //List<Integer> integerList = Arrays.asList();

        System.out.println("Result is : " + performMultiplication(integerList));
        Optional<Integer> result = performMultiplicationWithNoInitialValue(integerList);

        result.ifPresent(System.out::println);
        System.out.println(combineStudentNames());

        Optional<Student> highestGradeStudent = getHighestGradeStudent();
        highestGradeStudent.ifPresent(System.out::println);
    }
}
