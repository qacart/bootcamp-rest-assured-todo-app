package com.qacart.todo.testcases;

import com.github.javafaker.Faker;
import com.qacart.todo.clients.TodoClient;
import com.qacart.todo.clients.UserClient;
import com.qacart.todo.models.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddTodoTest {
    UserResponse userResponse;
    Faker faker;

    @BeforeMethod
    void setup() {
        faker = new Faker();
        User registerUser = User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();


        Response response = UserClient.registerApi(registerUser);

        userResponse = response.body().as(UserResponse.class);

        Assert.assertEquals(response.statusCode(), 201);

    }

    @Test
    void should_be_able_to_add_todo() {
        Todo todo = Todo.builder()
                .item(faker.book().title())
                .isCompleted(false).build();

        Response response = TodoClient.addTodoApi(todo, userResponse);

        TodoResponse todoResponse = response.body().as(TodoResponse.class);
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertFalse(todoResponse.isCompleted());
        Assert.assertNotNull(todoResponse.get_id());
        Assert.assertEquals(todoResponse.getItem(), todo.getItem());
        Assert.assertNotNull(todoResponse.getUserID());
        Assert.assertNotNull(todoResponse.getCreatedAt());
    }

    @Test
    void should_not_be_able_to_add_todo_without_title() {
        Todo todo = Todo.builder()
                .isCompleted(false).build();

        Response response = TodoClient.addTodoApi(todo, userResponse);

        ErrorResponse errorResponse = response.getBody().as(ErrorResponse.class);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(errorResponse.getMessage(), "\"item\" is required");
    }


}
