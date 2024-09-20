package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class) // this is necessary for Mockito DI. There can be only 1 runner with a class
public class TodoBusinessImplMockitoInjectMocksTest {
    @Mock
    private TodoService todoService; // this is the class whose mock Objects needs to be created

    @InjectMocks
    private TodoBusinessImpl todoBusinessImpl; // This is the class which will contain the mocking dependencies
    // Mockito will go and look into this class's definition and look for all fields annotated with @Mock here.
    // This is same as TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(TodoBusinessImpl);

    @Captor // this will create a Captor and inject it here. This can be used for all tests without needing to re-instantiation.
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void usingMockito() {
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
        List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Ranga");
        assertEquals(2, todos.size());
    }

    @Test
    public void usingMockito_UsingBDD() {
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        //given
        given(todoService.retrieveTodos("Ranga")).willReturn(allTodos);
        //when
        List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Ranga");
        //then
        assertThat(todos.size(), is(2));
    }

    @Test
    public void letsTestDeleteNow() {
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");
        verify(todoService).deleteTodo("Learn to Dance");
        verify(todoService, Mockito.never()).deleteTodo("Learn Spring MVC");
        verify(todoService, Mockito.never()).deleteTodo("Learn Spring");
        verify(todoService, Mockito.times(1)).deleteTodo("Learn to Dance");
        // atLeastOnce, atLeast
    }

    @Test
    public void captureArgument() {
        List<String> allTodos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        Mockito.when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Ranga");
        Mockito.verify(todoService).deleteTodo(stringArgumentCaptor.capture());
        assertEquals("Learn to Dance", stringArgumentCaptor.getValue());
    }
}
