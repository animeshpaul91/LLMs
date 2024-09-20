package com.Animesh.Java.FunctionalProgramming.Streams;

import com.Animesh.Java.FunctionalProgramming.data.Student;
import com.Animesh.Java.FunctionalProgramming.data.StudentDataBaseWithNotebooks;

import java.util.function.Predicate;

public class StreamsFilterMapReduceExample {
    private static final Predicate<Student> genderPredicate = (student -> student.getGender().equals("male"));
    private static final Predicate<Student> gradeLevel = (student -> student.getGradeLevel() >= 2.0);

    private static int noOfNoteBooks() {
        return StudentDataBaseWithNotebooks.getAllStudents().stream()
                .filter(genderPredicate)
                .filter(gradeLevel)
                .map((Student::getNoteBooks))
                // .reduce(0,(a,b)->a+b); //summing the notebooks.
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        System.out.println("Total No of NoteBooks are : " + noOfNoteBooks());
    }
}
