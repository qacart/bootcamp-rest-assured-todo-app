package com.qacart.todo.testcases;

import com.github.javafaker.Faker;
import com.qacart.todo.models.Todo;
import com.qacart.todo.models.TodoResponse;
import com.qacart.todo.models.User;
import com.qacart.todo.models.UserResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

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

        Response registerResponse = given()
                .baseUri("https://todo.qacart.com/api/v1")
                .contentType(ContentType.JSON)
                .body(registerUser)
                .when().post("/users/register")
                .then().extract().response();

        userResponse = registerResponse.body().as(UserResponse.class);
        Assert.assertEquals(registerResponse.statusCode(), 201);

        todo = Todo.builder()
                .item(faker.book().title())
                .isCompleted(false).build();

        Response addTodoResponse = given().baseUri("https://todo.qacart.com/api/v1")
                .contentType(ContentType.JSON)
                .body(todo)
                .auth().oauth2(userResponse.getAccess_token())
                .when().post("/tasks")
                .then().extract().response();

       todoResponse = addTodoResponse.body().as(TodoResponse.class);
       Assert.assertEquals(addTodoResponse.statusCode(), 201);
    }

    @Test
    void should_be_able_to_delete_todo() {
        Response response = given().baseUri("https://todo.qacart.com/api/v1")
                .pathParam("id", todoResponse.get_id())
                .auth().oauth2(userResponse.getAccess_token())
                .when().delete("/tasks/{id}")
                .then().extract().response();

        TodoResponse todoResponse = response.body().as(TodoResponse.class);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertFalse(todoResponse.isCompleted());
        Assert.assertNotNull(todoResponse.get_id());
        Assert.assertEquals(todoResponse.getItem(), todo.getItem());
        Assert.assertNotNull(todoResponse.getUserID());
        Assert.assertNotNull(todoResponse.getCreatedAt());
    }
}
