package com.example.demo.dtos;

import lombok.Data;

@Data
public class DeleteChatRequest {
    private String from;
    private String to;
}
