package com.example.userservicegradle.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "email cannot be null")
    @Size(min = 2, message = "email not be less than 2 characters")
    @Email
    private String email;

    @NotNull(message = "pwd cannot be null")
    @Size(min = 8, message = "pwd not be less than 8 characters")

    private String pwd;

    @NotNull(message = "name cannot be null")
    @Size(min = 2, message = "name not be less than 2 characters")
    private String name;

}
