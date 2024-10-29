package com.qacart.todo.clients;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    public static Response registerApi(User user) {
        return given()
                .baseUri("https://todo.qacart.com/api/v1")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/users/register")
                .then().extract().response();
    }

    public static Response loginApi(User loginUser) {
        return given()
                .baseUri("https://todo.qacart.com/api/v1")
                .contentType(ContentType.JSON)
                .body(loginUser)
                .when().post("/users/login")
                .then().extract().response();
    }

}
