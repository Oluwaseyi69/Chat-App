package com.example.demo.dtos;

import lombok.Data;

@Data
public class DeleteMessageRequest {
    private String from;
    private String to;
    private String messageBody;
}
