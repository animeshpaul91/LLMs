package models;

import java.io.Serializable;

public class Cat extends Animal implements Serializable {
    private final String name;

    public Cat(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String makeNoise() {
        return "My name is " + name + " Meow";
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
