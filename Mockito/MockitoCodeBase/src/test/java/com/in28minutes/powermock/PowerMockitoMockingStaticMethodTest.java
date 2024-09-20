package com.in28minutes.powermock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UtilityClass.class /*The class with static method to be mocked*/})
public class PowerMockitoMockingStaticMethodTest {
	/*
		To Mock Static Methods:
		1. Use Specific Runner [@PrepareForTest] (line # 17)
		2. Initialize Utility Class for Mocking (line # 39)
		3. Mock Method
	*/

    @Mock
    private Dependency dependencyMock;

    @InjectMocks
    private SystemUnderTest systemUnderTest;

    @Test
    public void powerMockito_MockingAStaticMethodCall() {
        when(dependencyMock.retrieveAllStats()).thenReturn(Arrays.asList(1, 2, 3));
        PowerMockito.mockStatic(UtilityClass.class); // getting this prepared has to be before line 37
        when(UtilityClass.staticMethod(anyLong())).thenReturn(150);
        int sum = systemUnderTest.methodCallingAStaticMethod();
        assertEquals(150, sum);

        //To verify a specific method call
        //First : Call PowerMockito.verifyStatic()
        //Second : Call the method to be verified
        PowerMockito.verifyStatic();
        UtilityClass.staticMethod(6);

        // PowerMockito.verifyStatic(); // this needs to be called everytime a method has to be verified
        // UtilityClass.staticMethod(-9);

        // verify exact number of calls
        PowerMockito.verifyStatic(Mockito.times(1));
    }
}