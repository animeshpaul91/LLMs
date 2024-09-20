package com.in28minutes.powermock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.stub;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SystemUnderTest.class /*To be able to mock the Constructor, we need to add in the Class that creates the new object*/})
public class PowerMockitoMockingConstructorTest {
	/*
		To Mock Static Methods:
		1. Use Specific Runner [@PrepareForTest] (line # 18) Use the class which "contains" the constructor
		2. Override the constructor (line # 36)
	*/

    private static final int SOME_DUMMY_SIZE = 100;

    @Mock
	private ArrayList<String> mockList;

    @InjectMocks
    private SystemUnderTest systemUnderTest;

    @Test
    public void powerMockito_MockingAConstructor() throws Exception {
        stub(mockList.size()).toReturn(SOME_DUMMY_SIZE);
        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(mockList);
        int size = systemUnderTest.methodUsingAnArrayListConstructor();
        assertEquals(SOME_DUMMY_SIZE, size);
    }
}