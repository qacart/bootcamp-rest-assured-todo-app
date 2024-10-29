package com.qacart.todo.clients;

import com.qacart.todo.models.User;
import com.qacart.todo.utils.ConfigUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    public static Response registerApi(User user) {
        return given()
                .baseUri(ConfigUtil.getInstance().getBaseUrl())
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/users/register")
                .then().extract().response();


    }

    public static Response loginApi(User loginUser) {
        return given()
                .baseUri(ConfigUtil.getInstance().getBaseUrl())
                .contentType(ContentType.JSON)
                .body(loginUser)
                .when().post("/users/login")
                .then().extract().response();
    }

}
