package com.in28minutes.junit.suite;

import com.in28minutes.junit.helper.StringHelperBooleanParameterizedTest;
import com.in28minutes.junit.helper.StringHelperParameterizedTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.in28minutes.junit.helper.ArraysTest;
import com.in28minutes.junit.helper.StringHelperTest;

@RunWith(Suite.class)
@SuiteClasses({ArraysTest.class, StringHelperTest.class, StringHelperParameterizedTest.class, StringHelperBooleanParameterizedTest.class})
public class DummyTestSuite {

}
