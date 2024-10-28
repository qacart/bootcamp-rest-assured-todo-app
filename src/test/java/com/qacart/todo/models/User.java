package com.qacart.todo.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
