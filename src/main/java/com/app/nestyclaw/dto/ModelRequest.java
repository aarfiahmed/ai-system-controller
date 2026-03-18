package com.app.nestyclaw.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ModelRequest {
    List<ChatMessage> messages;
    String model;
    private boolean stream = false;
}
