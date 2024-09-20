package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.learnjava.util.CommonUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamExampleTest {
    ParallelStreamExample parallelStreamExample;

    public ParallelStreamExampleTest() {
        this.parallelStreamExample = new ParallelStreamExample();
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void testStringTransformOutputSize(boolean toggle) {
        List<String> namesList = DataSet.namesList(); // given

        startTimer(); // when
        List<String> actualOutput = parallelStreamExample.stringTransform(namesList, toggle);
        timeTaken();
        stopWatchReset();

        assertEquals(4, actualOutput.size());
        actualOutput.forEach(name -> assertTrue(name.contains("-")));
    }
}