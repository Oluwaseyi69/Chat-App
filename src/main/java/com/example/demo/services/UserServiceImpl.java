package com.example.demo.services;

import com.example.demo.data.models.Chat;
import com.example.demo.data.models.User;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.dtos.*;
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
        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser(sendMessageRequest.getFrom());
        createChatRequest.setSecondUser(sendMessageRequest.getTo());

        FindChatRequest findChatRequest = new FindChatRequest();
        findChatRequest.setChatName(sendMessageRequest.getFrom() + " " + sendMessageRequest.getTo());
        findChatRequest.setParticipant(List
                .of(findByEmail(sendMessageRequest.getFrom())
                        , findByEmail(sendMessageRequest.getTo())));

        Chat chat = findChat(findChatRequest);

        if (chat == null) chat = createChat(createChatRequest);
        postMessage(sendMessageRequest, chat);

    }


    private Chat findChat(FindChatRequest findChatRequest) {
        return chatService.findChat(findChatRequest);
    }


    private void postMessage(SendMessageRequest sendMessageRequest, Chat chat) {
        messageService.sendMessage(sendMessageRequest, chat);
    }




}
