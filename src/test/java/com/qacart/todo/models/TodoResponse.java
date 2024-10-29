package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponse {
    @JsonProperty("isCompleted")
    private boolean isCompleted;
    private String _id;
    private String item;
    private String userID;
    private String createdAt;
    private int __v;
}

