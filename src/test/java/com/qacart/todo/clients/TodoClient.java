package com.qacart.todo.clients;

import com.qacart.todo.models.Todo;
import com.qacart.todo.models.TodoResponse;
import com.qacart.todo.models.UserResponse;
import com.qacart.todo.utils.ConfigUtil;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoClient {

    @Step
    public static Response addTodoApi(Todo todo, UserResponse userResponse) {
        return given().baseUri(ConfigUtil.getInstance().getBaseUrl())
                .contentType(ContentType.JSON)
                .body(todo)
                .auth().oauth2(userResponse.getAccess_token())
                .when().post("/tasks")
                .then().extract().response();
    }

    @Step
    public static Response deleteTodoApi(TodoResponse todoResponse, UserResponse userResponse) {
        return given().baseUri(ConfigUtil.getInstance().getBaseUrl())
                .pathParam("id", todoResponse.get_id())
                .auth().oauth2(userResponse.getAccess_token())
                .when().delete("/tasks/{id}")
                .then().extract().response();
    }
}
