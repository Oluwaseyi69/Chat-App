package com.example.demo.dtos;


import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
}
