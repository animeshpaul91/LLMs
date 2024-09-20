package models;

import java.util.List;

public class GenericMethods {
    private GenericMethods() {
    }

    public static <T> void shout(T thingToShout) {
        System.out.println(thingToShout + "!!!!! ");
    }

    public static <T, V> void doubleShout(T thingToShout, V otherThingToShout) {
        System.out.println(thingToShout + "!!!!! " + otherThingToShout + "!!!!");
    }

    public static void printList(final List<?> myList) {
        // ? means unknown
        // works with List of any type
        System.out.println("====");
        System.out.println(myList);
    }
    public static void printAnimalsList(final List<? extends Animal> myList) {
        // ? means unknown
        // works with List of any subtype of Animal
        System.out.println(myList);
    }
}
