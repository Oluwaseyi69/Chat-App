package com.example.demo.services;

import com.example.demo.data.models.User;
import com.example.demo.data.repository.ChatRepository;
import com.example.demo.data.repository.MessageRepository;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.dtos.*;
import com.example.demo.exception.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    public void doThisFirst(){
        userRepository.deleteAll();
        chatRepository.deleteAll();
        messageRepository.deleteAll();
    }

    @Test
    public void testThat_I_CanRegister_A_User(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Username");
        registerUserRequest.setPassword("password");

        userService.registerWith(registerUserRequest);
        assertThat(userRepository.count(), is(1L));
    }
    @Test
    public void testToCreate_A_Unique_User(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Username");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Username");
        registerUserRequest1.setPassword("password");
        assertThrows(UserAlreadyExistException.class,()->userService.registerWith(registerUserRequest1));
    }
    @Test
    public void testThat_A_UserCanCreateChat(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("seyi");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("anu");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        assertThat(userRepository.count(), is(2L));

        CreateChatRequest createChatRequest = new CreateChatRequest();
        createChatRequest.setFirstUser("seyi");
        createChatRequest.setSecondUser("anu");
        userService.createChat(createChatRequest);
        assertThat(chatRepository.count(), is(1L));

    }
    @Test
    public void testThatChatIsCreatedWhenUserSendsFirstMessage(){

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Seyi");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("Anu");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Seyi");
        sendMessageRequest.setTo("Anu");
        sendMessageRequest.setMessageBody("How are you doing?");
        userService.sendMessage(sendMessageRequest);

        assertThat(userRepository.count(), is(2L));
        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

    }

    @Test
    public void testThat_A_UserCanDelete_A_Message(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Seyi");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("anu");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Seyi");
        sendMessageRequest.setTo("anu");
        sendMessageRequest.setMessageBody("How are you doing?");
        userService.sendMessage(sendMessageRequest);


        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Seyi");
        sendMessageRequest1.setTo("anu");
        sendMessageRequest1.setMessageBody("It's Been a while since i heard from you");
        userService.sendMessage(sendMessageRequest1);
        assertThat(messageRepository.count(), is(2L));

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setFrom("Seyi");
        deleteMessageRequest.setTo("anu");
        deleteMessageRequest.setMessageBody("It's Been a while since i heard from you");
        userService.deleteMessage(deleteMessageRequest);
        assertThat(messageRepository.count(), is(1L));

    }

    @Test
    public void testThatUserCanViewChat(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Seyi");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("anu");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Seyi");
        sendMessageRequest.setTo("anu");
        sendMessageRequest.setMessageBody("How are you doing?");
        userService.sendMessage(sendMessageRequest);


        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Seyi");
        sendMessageRequest1.setTo("anu");
        sendMessageRequest1.setMessageBody("It's Been a while since i heard from you");
        userService.sendMessage(sendMessageRequest1);

        List<String> messages = userService.viewMessages("Seyi", "anu");
        assertThat(messages.size(), is(2));


    }

    @Test
    public void testTheContentOfTheLastMessage(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Seyi");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("anu");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Seyi");
        sendMessageRequest.setTo("anu");
        sendMessageRequest.setMessageBody("How are you doing?");
        userService.sendMessage(sendMessageRequest);


        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Seyi");
        sendMessageRequest1.setTo("anu");
        sendMessageRequest1.setMessageBody("We're unstoppable");
        userService.sendMessage(sendMessageRequest1);

        List<String> messages = userService.viewMessages("Seyi", "anu");
        String lastMessage = messages.get(messages.size()-1);
        assertThat(lastMessage, containsString("We're unstoppable"));

    }

    @Test
    public void testThatUser_Can_DeleteChat(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Seyi");
        registerUserRequest.setPassword("password");
        userService.registerWith(registerUserRequest);

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setUsername("anu");
        registerUserRequest1.setPassword("password");
        userService.registerWith(registerUserRequest1);

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom("Seyi");
        sendMessageRequest.setTo("anu");
        sendMessageRequest.setMessageBody("How are you doing?");
        userService.sendMessage(sendMessageRequest);


        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

        SendMessageRequest sendMessageRequest1 = new SendMessageRequest();
        sendMessageRequest1.setFrom("Seyi");
        sendMessageRequest1.setTo("anu");
        sendMessageRequest1.setMessageBody("It's Been a while since i heard from you");
        userService.sendMessage(sendMessageRequest1);
        assertThat(messageRepository.count(), is(2L));

        DeleteChatRequest deleteChatRequest = new DeleteChatRequest();
        deleteChatRequest.setFrom("Seyi");
        deleteChatRequest.setTo("anu");

        userService.deleteChat(deleteChatRequest);
        assertThat(chatRepository.count(), is(0L));


    }

}