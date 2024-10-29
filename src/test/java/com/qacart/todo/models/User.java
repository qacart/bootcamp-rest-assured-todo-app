package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
