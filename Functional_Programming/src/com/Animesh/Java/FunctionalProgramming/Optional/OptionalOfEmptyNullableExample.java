package com.Animesh.Java.FunctionalProgramming.Optional;

import java.util.Optional;

public class OptionalOfEmptyNullableExample {
    public static Optional<String> ofNullable() {
        return Optional.ofNullable(null);
    }

    public static Optional<String> of() {
        return Optional.of("Hello");
        // return  Optional.of(null);
    }

    public static Optional<String> empty() {
        return Optional.empty();
        // return  Optional.of(null);
    }

    public static void main(String[] args) {
        System.out.println(ofNullable()); // same as Optional.empty()
        System.out.println(of());
        System.out.println(empty());
    }
}
