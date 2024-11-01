package com.qacart.todo.testcases;

import com.github.javafaker.Faker;
import com.qacart.todo.clients.UserClient;
import com.qacart.todo.models.ErrorResponse;
import com.qacart.todo.models.User;
import com.qacart.todo.models.UserResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTest {

    User registerUser;

    @BeforeMethod
    void setup() {
        Faker faker = new Faker();
        registerUser = User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();

        Response response = UserClient.registerApi(registerUser);
        Assert.assertEquals(response.statusCode(), 201);

    }

    @Test
    void should_be_able_to_login() {
        User loginUser = User.builder()
                .email(registerUser.getEmail())
                .password(registerUser.getPassword())
                .build();

        Response response = UserClient.loginApi(loginUser);

        UserResponse userResponse = response.body().as(UserResponse.class);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(userResponse.getFirstName(), registerUser.getFirstName());
        Assert.assertNotNull(userResponse.getAccess_token());
        Assert.assertNotNull(userResponse.getUserID());
    }

    @Test
    void should_not_be_able_to_login_using_wrong_password() {
        User loginUser = User.builder()
                .email(registerUser.getEmail())
                .password("WrongPassword")
                .build();

        Response response = UserClient.loginApi(loginUser);

        ErrorResponse errorResponse = response.body().as(ErrorResponse.class);
        Assert.assertEquals(response.statusCode(), 401);
        Assert.assertEquals(errorResponse.getMessage()
                , "The email and password combination is not correct, please fill a correct email and password");
    }
}
