package com.Animesh.Comparators;

public class Account {
    private final String name;
    private final int age;
    private double balance;

    public Account(String name, int age, double balance) {
        this.name = name;
        this.age = age;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return name + " " + age + " " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        Account obj = (Account) o;
        return this.name.equalsIgnoreCase(obj.getName());
    }
}
