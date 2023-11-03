package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.Message;
import com.example.demo.dtos.FindMessageRequest;
import com.example.demo.dtos.SendMessageRequest;

import java.util.List;

public interface MessageService {
    void sendMessage(SendMessageRequest sendMessageRequest, Chat chat);
    Message findMessage (FindMessageRequest findMessageRequest);
    void delete(Message message);

    List<String> findAllMessages(Chat chat);
}
