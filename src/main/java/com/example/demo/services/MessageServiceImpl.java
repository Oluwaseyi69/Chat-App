package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.Message;
import com.example.demo.data.repository.MessageRepository;
import com.example.demo.dtos.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Override
    public void sendMessage(SendMessageRequest sendMessageRequest, Chat chat) {
        Message message = new Message();
        message.setBody(sendMessageRequest.getMessageBody());
        message.setChatId(chat.getId());
        message.setRead(false);
        message.setDateCreated(sendMessageRequest.getSentDate());
        messageRepository.save(message);

    }
}
