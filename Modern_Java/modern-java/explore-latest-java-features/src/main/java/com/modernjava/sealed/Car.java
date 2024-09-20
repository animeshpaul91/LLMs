package com.modernjava.sealed;

public sealed class Car extends Vehicle implements SmartMediaPlayer permits FlyingCar {
    @Override
    public String drive() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void connectPhone() {
        System.out.println("Phone Connected");
    }
}
