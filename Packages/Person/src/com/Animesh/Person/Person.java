package com.Animesh.Person;

public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        // System.out.println("Animesh");
        String objName = ((Person) obj).getName();
        return name.equals(objName);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
        // return 0;
    }
}
