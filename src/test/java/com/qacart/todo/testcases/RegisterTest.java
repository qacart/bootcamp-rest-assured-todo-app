package com.qacart.todo.testcases;

import com.github.javafaker.Faker;
import com.qacart.todo.models.User;
import com.qacart.todo.models.UserResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RegisterTest {

    @Test
    void shouldBeAbleToRegister() {

        Faker faker = new Faker();

        User user = User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();

        Response response = given()
                .baseUri("https://todo.qacart.com/api/v1")
                .contentType(ContentType.JSON)
                .body(user)
                .when().post("/users/register")
                .then().extract().response();

        UserResponse userResponse = response.body().as(UserResponse.class);

        Assert.assertEquals(userResponse.getFirstName(), user.getFirstName());


    }
}
