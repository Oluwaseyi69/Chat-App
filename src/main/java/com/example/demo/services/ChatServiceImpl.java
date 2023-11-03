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
                        .getFirstChatName(),findChatRequest.getParticipant());

        if(chat.isPresent()) return chat.get();
        Optional<Chat> repeatedChat = recheckRepo(findChatRequest);
        return repeatedChat.orElse(null);
    }

    @Override
    public void delete(Chat chat) {
        chatRepository.delete(chat);
    }

    @Override
    public void createChat(Chat chat) {
        chatRepository.save(chat);

    }
    private Optional<Chat> recheckRepo(FindChatRequest findChatRequest){
        return chatRepository.findChatByChatNameContainingAndParticipantIn(
                findChatRequest.getSecondChatName(),
                findChatRequest.getParticipant());
    }


}
