package com.qacart.todo.testcases;

import com.github.javafaker.Faker;
import com.qacart.todo.clients.TodoClient;
import com.qacart.todo.clients.UserClient;
import com.qacart.todo.models.Todo;
import com.qacart.todo.models.TodoResponse;
import com.qacart.todo.models.User;
import com.qacart.todo.models.UserResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteTodoTest {

    UserResponse userResponse;
    TodoResponse todoResponse;
    Todo todo;

    @BeforeMethod
    void setup() {
        // Register new user and get the token
        Faker faker = new Faker();
        User registerUser = User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();

        Response registerResponse = UserClient.registerApi(registerUser);

        userResponse = registerResponse.body().as(UserResponse.class);
        Assert.assertEquals(registerResponse.statusCode(), 201);

        todo = Todo.builder()
                .item(faker.book().title())
                .isCompleted(false).build();

        Response addTodoResponse = TodoClient.addTodoApi(todo, userResponse);

       todoResponse = addTodoResponse.body().as(TodoResponse.class);
       Assert.assertEquals(addTodoResponse.statusCode(), 201);
    }

    @Test
    void should_be_able_to_delete_todo() {
        Response response = TodoClient.deleteTodoApi(todoResponse, userResponse);

        TodoResponse todoResponse = response.body().as(TodoResponse.class);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertFalse(todoResponse.isCompleted());
        Assert.assertNotNull(todoResponse.get_id());
        Assert.assertEquals(todoResponse.getItem(), todo.getItem());
        Assert.assertNotNull(todoResponse.getUserID());
        Assert.assertNotNull(todoResponse.getCreatedAt());
    }
}
