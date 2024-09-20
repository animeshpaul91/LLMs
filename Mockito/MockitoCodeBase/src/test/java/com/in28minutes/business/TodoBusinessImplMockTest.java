package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoBusinessImplMockTest {

    @Test
    public void testUsingMockWithNonEmptyOutput() {
        TodoService todoServiceMock = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        // dynamically stubbing a method
        when(todoServiceMock.retrieveTodos("Animesh")).thenReturn(allTodos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
        List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Animesh");
        List<String> expected = Arrays.asList("Learn Spring MVC", "Learn Spring");
        assertEquals(expected, todos);
    }

    @Test
    public void testUsingMockWithEmptyOutput() {
        TodoService todoServiceMock = mock(TodoService.class);
        // no need to tell mockito what to do. By default, if not told, it returns an empty list
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
        List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Animesh");
        assertEquals(0, todos.size());
    }

    @Test
    public void testUsingMockWithGenuinelyEmptyOutput() {
        TodoService todoServiceMock = mock(TodoService.class);
        List<String> allTodos = Arrays.asList("Google", "Facebook", "Byte Dance", "Twitter", "Udemy");
        when(todoServiceMock.retrieveTodos("companies")).thenReturn(allTodos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
        List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("companies");
        assertTrue(todos.isEmpty());
    }
}
