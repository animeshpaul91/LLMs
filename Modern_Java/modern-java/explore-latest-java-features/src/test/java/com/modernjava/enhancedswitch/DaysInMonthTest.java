package com.modernjava.enhancedswitch;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysInMonthTest {

    @ParameterizedTest
    @MethodSource("input")
    void getDaysUsingOldSwitch(Month month, int expectedNoOfDays) {
        int days = DaysInMonth.getDaysUsingOldSwitch(month, 2023);
        assertEquals(expectedNoOfDays, days);
    }

    @ParameterizedTest
    @MethodSource("input")
    void getDaysUsingNewSwitch(Month month, int expectedNoOfDays) {
        int days = DaysInMonth.getDaysUsingNewSwitch(month, 2023);
        assertEquals(expectedNoOfDays, days);
    }

    @ParameterizedTest
    @MethodSource("input")
    void getDaysUsingExhaustiveSwitch(Month month, int expectedNoOfDays) {
        int days = DaysInMonth.getDaysUsingExhaustiveSwitch(month, 2023);
        assertEquals(expectedNoOfDays, days);
    }


    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of(Month.FEBRUARY, 28),
                Arguments.of(Month.SEPTEMBER, 30),
                Arguments.of(Month.JANUARY, 31)
        );
    }
}