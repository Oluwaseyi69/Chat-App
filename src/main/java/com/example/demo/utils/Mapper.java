package com.example.demo.utils;

import com.example.demo.data.models.User;
import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.RegisterUserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setEmail(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());

        return user;
    }
    public static RegisterUserResponse map(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getEmail());
        registerUserResponse.setRegisterDate(DateTimeFormatter
                .ofPattern("EEE dd/MMM/yyyy HH:mm:ss a")
                .format(LocalDateTime.now()));
        return registerUserResponse;
    }
}
