package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.dtos.SendMessageRequest;

public interface MessageService {
    void sendMessage(SendMessageRequest sendMessageRequest, Chat chat);
}
