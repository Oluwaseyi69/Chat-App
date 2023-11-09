package com.example.demo.dtos;

import lombok.Data;

@Data
public class FindMessageRequest {

    private String chatId;
    private String messageBody;
}
