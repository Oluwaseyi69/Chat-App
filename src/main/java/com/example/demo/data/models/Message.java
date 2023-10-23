package com.example.demo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("Messages")
public class Message {
    @Id
    private String id;
    private LocalDate dateCreated;
    private String body;
    private boolean isRead;
    private String chatId;

}
