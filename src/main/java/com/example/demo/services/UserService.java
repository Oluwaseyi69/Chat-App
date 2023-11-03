package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.User;
import com.example.demo.dtos.*;

import java.util.List;

public interface UserService {

    RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest);
    Chat createChat(CreateChatRequest createChatRequest);

    User findByEmail(String username);
    void sendMessage(SendMessageRequest sendMessageRequest);
    void deleteMessage(DeleteMessageRequest deleteMessageRequest);
    void deleteChat(DeleteChatRequest deleteChatRequest);

    List<String> viewMessages(String firstUser, String secondUser);
}
