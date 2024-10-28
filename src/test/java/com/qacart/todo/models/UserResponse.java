package com.qacart.todo.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {
    private String access_token;
    private String firstName;
    private String userID;
}
