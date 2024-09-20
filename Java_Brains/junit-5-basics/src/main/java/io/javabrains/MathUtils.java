package io.javabrains;

public class MathUtils {

    protected int add(int a, int b) { // protected makes it accessible to test since the test class is in the same package
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        return a / b;
    }

    public int mod(int a, int b) {
        return a % b;
    }

    public double computeCircleArea(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }
}
