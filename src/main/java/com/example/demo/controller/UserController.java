package com.example.demo.controller;

import com.example.demo.dtos.DeleteChatRequest;
import com.example.demo.dtos.DeleteMessageRequest;
import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.SendMessageRequest;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("xtalker")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public String register(@RequestBody RegisterUserRequest registerUserRequest) {
        try {
            userService.registerWith(registerUserRequest);
            return "Successfully Registered";
        } catch (Exception error) {
            return error.getMessage();
        }
    }


    @PostMapping("/message")
    public String message (@RequestBody SendMessageRequest sendMessageRequest){
        try {
            userService.sendMessage(sendMessageRequest);
            return "Message Successfully Sent";
        } catch (Exception error){
            return error.getMessage();
        }
    }

    @DeleteMapping("/DeleteMessage")
    public String deleteMessage(@RequestBody DeleteMessageRequest deleteMessageRequest){
        try {
            userService.deleteMessage(deleteMessageRequest);
            return "Message Deleted";
        }catch (Exception error){
            return error.getMessage();
        }
    }
    @DeleteMapping("/Delete Chat")
    public String deleteChat(@RequestBody DeleteChatRequest deleteChatRequest){
        try{
            userService.deleteChat(deleteChatRequest);
            return "Chat deleted";
        }catch (Exception error){
            return error.getMessage();
        }
    }
    @GetMapping("/viewChat/{firstUser}/{secondUser}")
    public List<String> viewChat(@PathVariable String firstUser, @PathVariable String secondUser){
        try {
            return userService.viewMessages(firstUser, secondUser);
        }catch (Exception error){
            return List.of(error.getMessage());
        }
    }

}