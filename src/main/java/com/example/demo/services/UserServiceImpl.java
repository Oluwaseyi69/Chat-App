package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.Message;
import com.example.demo.data.models.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.dtos.*;
import com.example.demo.exception.ChatNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.Mapper.map;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;


    @Override
    public RegisterUserResponse registerWith(RegisterUserRequest registerUserRequest) {
        findUser(registerUserRequest);
        return map(userRepository.save(map(registerUserRequest)));
    }

    @Override
    public Chat createChat(CreateChatRequest createChatRequest) {
        Chat chat = new Chat();
        chat.setChatName(createChatRequest.getFirstUser() + " " + createChatRequest.getSecondUser());
        chat.getParticipant().addAll(List
                .of(findByEmail(createChatRequest.getFirstUser()),findByEmail(createChatRequest.getSecondUser())));
        chatService.createChat(chat);
        return chat;
    }

    @Override
    public User findByEmail(String username) {
        Optional<User> foundUser = userRepository.findByEmail(username);
        if(foundUser.isPresent())
            return foundUser.get();
        throw new UserAlreadyExistException("Oga your details no dey here");
    }
    private void findUser(RegisterUserRequest registerUserRequest){
        Optional<User> user = userRepository.findByEmail(registerUserRequest.getUsername());
        if(user.isPresent())
            throw new UserAlreadyExistException("Person dey answer that name");

    }

    @Override
    public void sendMessage(SendMessageRequest sendMessageRequest) {
        CreateChatRequest createChatRequest = map(sendMessageRequest);
        Chat chat = findChatWithUser(sendMessageRequest.getFrom(), sendMessageRequest.getTo());

        if (chat == null) chat = createChat(createChatRequest);
        postMessage(sendMessageRequest, chat);

    }

    @Override
    public void deleteMessage(DeleteMessageRequest deleteMessage) {
        FindMessageRequest findMessageRequest = new FindMessageRequest();
        Chat chat = findChatWithUser(deleteMessage.getFrom(), deleteMessage.getTo());

        validateChat(chat);

        findMessageRequest.setChatId(chat.getId());
        findMessageRequest.setMessageBody(deleteMessage.getMessageBody());
        Message message = messageService.findMessage(findMessageRequest);
        messageService.delete(message);
    }

    private static void validateChat(Chat chat){
        if (chat == null) throw new ChatNotFoundException("Chat not found");
    }

    @Override
    public void deleteChat(DeleteChatRequest deleteChatRequest) {
        Chat chat = findChatWithUser(deleteChatRequest.getFrom(), deleteChatRequest.getTo());
        validateChat(chat);
        chatService.delete(chat);
    }


    private Chat findChat(FindChatRequest findChatRequest) {
        return chatService.findChat(findChatRequest);
    }


    private void postMessage(SendMessageRequest sendMessageRequest, Chat chat) {
        messageService.sendMessage(sendMessageRequest, chat);
    }

    private Chat findChatWithUser(String firstUser, String secondUser){
        FindChatRequest findChatRequest =new FindChatRequest();
         findChatRequest.setFirstChatName(firstUser + " " + secondUser);
         findChatRequest.setSecondChatName(secondUser + " " + firstUser);
         findChatRequest.setParticipant(List.of(findByEmail(firstUser), findByEmail(secondUser)));
        return findChat(findChatRequest);
    }

    public List<String> viewMessages(String firstUser, String secondUser) {
        Chat foundChat = findChatWithUser(firstUser, secondUser);
        validateChat(foundChat);

        return messageService.findAllMessages(foundChat);
    }



}
