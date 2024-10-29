package com.qacart.todo.clients;

import com.qacart.todo.models.Todo;
import com.qacart.todo.models.TodoResponse;
import com.qacart.todo.models.UserResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoClient {

    public static Response addTodoApi(Todo todo, UserResponse userResponse) {
        return given().baseUri("https://todo.qacart.com/api/v1")
                .contentType(ContentType.JSON)
                .body(todo)
                .auth().oauth2(userResponse.getAccess_token())
                .when().post("/tasks")
                .then().extract().response();
    }

    public static Response deleteTodoApi(TodoResponse todoResponse, UserResponse userResponse) {
        return given().baseUri("https://todo.qacart.com/api/v1")
                .pathParam("id", todoResponse.get_id())
                .auth().oauth2(userResponse.getAccess_token())
                .when().delete("/tasks/{id}")
                .then().extract().response();
    }
}
