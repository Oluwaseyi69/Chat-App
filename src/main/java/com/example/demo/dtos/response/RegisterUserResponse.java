package com.example.demo.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterUserResponse {
    private String username;
    private String registerDate;
}
