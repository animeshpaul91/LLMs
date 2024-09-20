import models.Animal;
import models.AnimalPrinter;
import models.Cat;
import models.Dog;
import models.GenericMap;
import models.GenericMethods;
import models.GenericPrinter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final GenericPrinter<Integer> integerPrinter = new GenericPrinter<>(10);
        integerPrinter.print();

        final GenericPrinter<String> stringPrinter = new GenericPrinter<>("Animesh Paul");
        stringPrinter.print();

        int[] array = {1, 2, 3, 4};
        final GenericPrinter<int[]> arrayPrinter = new GenericPrinter<>(array);
        arrayPrinter.print(); // prints gibberish

        final Dog dog = new Dog("Dash");
        final AnimalPrinter<Dog> dogPrinter = new AnimalPrinter<>(dog);
        dogPrinter.print();

        final Cat cat = new Cat("Rosette");
        final AnimalPrinter<Cat> catPrinter = new AnimalPrinter<>(cat);
        catPrinter.print();

        GenericMethods.shout("Animesh Paul");
        GenericMethods.doubleShout("Animesh Paul", dog);
        GenericMethods.doubleShout("Animesh Paul", cat);

        GenericMap<String, Animal> nameToAnimalMap = new GenericMap<>();
        final String dogName = dog.getName();
        System.out.println(nameToAnimalMap.put(dogName, dog));
        final String catName = cat.getName();
        System.out.println(nameToAnimalMap.put(catName, cat));

        assert nameToAnimalMap.get(dogName).equals(dog);
        assert nameToAnimalMap.get(catName).equals(cat);

        GenericMethods.printList(List.of(1, 2, 3, 4));

        final List<Cat> cats = List.of(cat);
        final List<Dog> dogs = List.of(dog);

        GenericMethods.printAnimalsList(cats);
        GenericMethods.printAnimalsList(dogs);
    }
}