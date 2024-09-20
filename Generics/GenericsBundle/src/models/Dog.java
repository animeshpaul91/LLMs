package models;

import java.io.Serializable;

public class Dog extends Animal implements Serializable {
    private final String name;

    public Dog(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String makeNoise() {
        return "My name is " + name + " Woof";
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}
