package com.example.demo.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SendMessageRequest {
    private String from;
    private String to;
    private String messageBody;
    private LocalDate sentDate = LocalDate.now();
    private LocalDate sentTime = LocalDate.now();


}
