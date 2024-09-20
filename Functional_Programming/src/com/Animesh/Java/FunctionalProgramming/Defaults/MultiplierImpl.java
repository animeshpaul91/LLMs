package com.Animesh.Java.FunctionalProgramming.Defaults;

import com.Animesh.Java.FunctionalProgramming.Defaults.Interfaces.Multiplier;

import java.util.List;

public class MultiplierImpl implements Multiplier {

    @Override
    public int multiply(List<Integer> integerList) {

        return integerList
                .stream()
                .reduce(1, (x, y) -> x * y);
    }

    @Override
    public int size(List<Integer> integerList) {
        System.out.println("Inside Implementation class");
        return integerList.size();
    }

}
