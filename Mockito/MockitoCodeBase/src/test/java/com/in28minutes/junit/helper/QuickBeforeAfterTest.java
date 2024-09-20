package com.in28minutes.junit.helper;

import org.junit.*;

public class QuickBeforeAfterTest {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before Class");
    }

    @Before // runs before every test
    public void setup() { // this gets invoked/setup before every test executes
        System.out.println("Before Test"); // each test runs with a new instance of StringHelper
    }

    @Test
    public void test1() {
        System.out.println("test1 executed");
    }

    @Test
    public void test2() {
        System.out.println("test2 executed");
    }

    @After
    public void teardown() {
        System.out.println("After test");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After Class");
    }

}
