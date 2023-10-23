package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.User;
import com.example.demo.dtos.CreateChatRequest;
import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.RegisterUserResponse;
import com.example.demo.dtos.SendMessageRequest;

public interface UserService {

    RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest);
    Chat createChat(CreateChatRequest createChatRequest);

    User findByEmail(String username);
    void sendMessage(SendMessageRequest sendMessageRequest);
}
