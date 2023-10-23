package com.example.demo.dtos;

import com.example.demo.data.models.User;
import lombok.Data;

import java.util.List;

@Data
public class FindChatRequest {
    private String chatName;
    private List<User>participant;
}
