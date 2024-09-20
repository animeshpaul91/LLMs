package com.Animesh.Java.FunctionalProgramming.Defaults;


import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBase;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class DefaultMethodsExample2 {
    private static final Comparator<Student> nameComparator = Comparator.comparing(Student::getName);
    private static final Comparator<Student> gpaComparator = Comparator.comparing(Student::getGpa);
    private static final Comparator<Student> genderComparator = Comparator.comparing(Student::getGender);
    private static final Comparator<Student> gradeComparator = Comparator.comparing(Student::getGradeLevel);
    private static final Consumer<Student> studentConsumer = student -> System.out.println("student : " + student);

    public static void sortByName(List<Student> studentList) {
        studentList.sort(Comparator.comparing(Student::getName)); // inline
        studentList.sort(nameComparator); // Using a reference
        System.out.println("After Sort BY Name : ");
        studentList.forEach(studentConsumer);
    }

    public static void sortByGPA(List<Student> studentList) {
        studentList.sort(gpaComparator);
        System.out.println("After Sort BY GPA : ");
        studentList.forEach(studentConsumer);
    }

    public static void sortByGender() {
        List<Student> studentList = StudentDataBase.getAllStudents();
        Comparator<Student> nullLast = Comparator.nullsFirst(genderComparator); // this will handle nulls in the studentList collection
        studentList.sort(nullLast);
        System.out.println("After Sort By Gender : ");
        // nulls will be placed at the beginning in the sorted collection
        studentList.forEach(studentConsumer);
    }


    public static void comparatorChaining() {
        List<Student> studentList = StudentDataBase.getAllStudents();
        studentList.sort(gradeComparator.thenComparing(nameComparator));
        System.out.println("Comparator Chaining List");
        studentList.forEach(studentConsumer);
    }

    public static void main(String[] args) {
        List<Student> studentList = StudentDataBase.getAllStudents();
        System.out.println("Original List");
        studentList.forEach(student -> System.out.println("student : " + student));
        /*sortByGender();
        sortByName(studentList);
        sortByGPA(studentList);*/
        comparatorChaining();
    }
}
