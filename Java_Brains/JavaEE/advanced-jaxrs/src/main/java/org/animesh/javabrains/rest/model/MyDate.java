package org.animesh.javabrains.rest.model;

public class MyDate {
    private final int date;
    private final int month;
    private final int year;

    public MyDate(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "date=" + date +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
