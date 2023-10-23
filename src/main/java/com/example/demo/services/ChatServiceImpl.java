package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.repository.ChatRepository;
import com.example.demo.dtos.FindChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepository;


    @Override
    public Chat findChat(FindChatRequest findChatRequest) {
        Optional<Chat>chat = chatRepository
                .findChatByChatNameContainingAndParticipantIn(findChatRequest
                        .getChatName(),findChatRequest.getParticipant());
        return chat.orElse(null);
    }

    @Override
    public void createChat(Chat chat) {
        chatRepository.save(chat);
    }
}
