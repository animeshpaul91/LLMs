package com.modernjava.sealed;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestVehicle() {
        final var car = new Car();
        final var truck = new Truck();

        assertInstanceOf(Vehicle.class, car);
        assertInstanceOf(Vehicle.class, truck);
    }

    @Test
    void testDrive() {
        final var car = new Car();
        final var truck = new Truck();
        final var flyingCar = new FlyingCar();
        assertEquals("Car", car.drive());
        assertEquals("Truck", truck.drive());
        assertEquals("FlyingCar", flyingCar.drive());
    }
}