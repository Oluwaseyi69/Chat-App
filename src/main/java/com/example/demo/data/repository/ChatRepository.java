package com.example.demo.data.repository;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Optional<Chat> findChatByChatNameContainingAndParticipantIn(String chatName, List<User>participant);
}
