package models;

import java.io.Serializable;

public record AnimalPrinter<T extends Animal & Serializable>(T animal) {
    // you have to mention the class first followed by all the n number of interfaces
    // type T is anything that is an Animal and a Serializable at the same time
    public void print() {
        System.out.println(animal.makeNoise());
    }
}
