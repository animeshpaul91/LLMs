package com.in28minutes.business;

import com.in28minutes.data.api.TodoService;
import com.in28minutes.data.stub.TodoServiceStub;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TodoBusinessImplStubTest {

    @Test
    public void usingAStub() {
        TodoService todoService = new TodoServiceStub();
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
        List<String> todos = todoBusinessImpl
                .retrieveTodosRelatedToSpring("Ranga");
        List<String> expected = Arrays.asList("Learn Spring MVC", "Learn Spring");
        // assertEquals(2, todos.size());
        assertEquals(expected, todos);
    }
}
