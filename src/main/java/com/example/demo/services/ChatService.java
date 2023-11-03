package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.dtos.FindChatRequest;

public interface ChatService {
    void createChat(Chat chat);
    Chat findChat(FindChatRequest findChatRequest);

    void delete(Chat chat);
}
