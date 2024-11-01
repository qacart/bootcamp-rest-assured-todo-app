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

public class RegisterTest {

    Faker faker;

    @BeforeMethod
    void setup() {
        faker = new Faker();
    }

    @Test
    void should_be_able_to_register() {
        User user = User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();

        Response response = UserClient.registerApi(user);

        UserResponse userResponse = response.body().as(UserResponse.class);
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(userResponse.getFirstName(), user.getFirstName());
        Assert.assertNotNull(userResponse.getAccess_token());
        Assert.assertNotNull(userResponse.getUserID());
    }

    @Test
    void should_not_be_able_to_register_without_first_name() {
        User user = User.builder()
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();


        Response response = UserClient.registerApi(user);

        ErrorResponse errorResponse = response.body().as(ErrorResponse.class);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(errorResponse.getMessage(), "\"firstName\" is required");
    }

    @Test
    void should_not_be_able_to_register_without_email() {
        User user = User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .password(faker.internet().password())
                .build();

        Response response = UserClient.registerApi(user);

        ErrorResponse errorResponse = response.body().as(ErrorResponse.class);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(errorResponse.getMessage(), "\"email\" is required");
    }
}
