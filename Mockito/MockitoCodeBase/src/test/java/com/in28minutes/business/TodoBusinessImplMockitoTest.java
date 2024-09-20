package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class TodoBusinessImplMockitoTest {

    @Test
    public void usingMockito() {
        TodoService todoService = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Learn Spring MVC",
                "Learn Spring", "Learn to Dance");
        when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        List<String> todos = todoBusinessImpl
                .retrieveTodosRelatedToSpring("Ranga");
        assertEquals(2, todos.size());
    }

    // Using BDD
    @Test
    public void usingMockito_UsingBDD() {
        TodoService todoService = mock(TodoService.class);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        // Given
        given(todoService.retrieveTodos("Ranga")).willReturn(allTodos);
        // When
        List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Ranga");
        // Then
        assertThat(todos.size(), is(2));
    }

    @Test
    public void letsTestDeleteNow() {
        // Given
        TodoService todoService = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        given(todoService.retrieveTodos("Ranga")).willReturn(allTodos);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");
        // Then
        verify(todoService).deleteTodo("Learn to Dance"); // verifying deleteTodo was called on todoService with arg - "Learn to Dance"
        verify(todoService, never()).deleteTodo("Learn Spring MVC"); // verifying that this was never called
        verify(todoService, never()).deleteTodo("Learn Spring");
        verify(todoService, times(1)).deleteTodo("Learn to Dance"); // 1 is the number of times
        verify(todoService, atLeastOnce()).deleteTodo("Learn to Dance");
        verify(todoService, atLeast(1)).deleteTodo("Learn to Dance"); // same thing as line 62
        // verifying calls are required when services(dependencies) do not return values and tests need to test "side effects".
    }

    @Test
    public void letsTestDeleteNowUsingBDDStyle() {
        // Given
        TodoService todoService = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        given(todoService.retrieveTodos("Ranga")).willReturn(allTodos);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");
        // Then
        then(todoService).should().deleteTodo("Learn to Dance"); // verifying deleteTodo was called on todoService with arg - "Learn to Dance"
        then(todoService).should(never()).deleteTodo("Learn Spring MVC"); // verifying that this was never called
        then(todoService).should(never()).deleteTodo("Learn Spring"); // verifying that this was never called
        then(todoService).should(times(1)).deleteTodo("Learn to Dance"); // 1 is the number of times
        then(todoService).should(atLeastOnce()).deleteTodo("Learn to Dance");
        then(todoService).should(atLeast(1)).deleteTodo("Learn to Dance"); // same thing as line 62
        // verifying calls are required when services(dependencies) do not return values and tests need to test "side effects".
    }

    @Test
    public void captureArgument() {
        // Declare Argument Captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        TodoService todoService = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        Mockito.when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");

        // Define Argument Captor
        then(todoService).should().deleteTodo(argumentCaptor.capture());
        // verify(todoService).deleteTodo(argumentCaptor.capture()); // same thing as previous line
        assertThat(argumentCaptor.getValue(), is("Learn to Dance"));
    }

    @Test
    public void captureMultipleArguments() {
        // Declare Argument Captor
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        TodoService todoService = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Learn to Rock and Roll", "Learn Spring", "Learn to Dance");
        Mockito.when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");

        // Define Argument Captor
        then(todoService).should(times(2)).deleteTodo(argumentCaptor.capture()); // default value is 1 without times
        // verify(todoService).deleteTodo(argumentCaptor.capture()); // same thing as previous line
        assertThat(argumentCaptor.getAllValues().size(), is(2));
    }
}
