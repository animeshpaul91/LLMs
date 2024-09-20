package com.learnjava.completablefutures;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureExceptionHandlingTest {

    @Mock
    private HelloWorldService helloWorldService;

    @InjectMocks
    private CompletableFutureExceptionHandling cfEH;

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionOnHello() {
        // Given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod(); // Cool Stuff!

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingHandle();

        // Then
        assertEquals(" WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionOnWorld() {
        // Given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingHandle();

        // Then
        assertEquals(" HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionOnHappyPath() {
        // Given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingHandle();

        // Then
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionallyHappyPath() {
        // Given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionally();

        // Then
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionallyOnBoth() {
        // Given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionally();

        // Then
        assertEquals("", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionallyOnHello() {
        // Given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenCallRealMethod();

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionally();

        // Then
        assertEquals("", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionallyOnWorld() {
        // Given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingExceptionally();

        // Then
        assertEquals("", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingWhenCompleteSuccess() {
        // Given
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingWhenComplete();
        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE!", result);
    }

    @Test
    void thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingWhenCompleteFailure() {
        // Given
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception Occurred"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception Occurred"));

        // When
        String result = cfEH.thenCombineExampleWithThreeAsyncCallsHandleExceptionUsingWhenComplete();
        assertEquals(" HI COMPLETABLE FUTURE!", result);
    }
}