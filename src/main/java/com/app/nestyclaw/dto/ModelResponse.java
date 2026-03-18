package com.app.nestyclaw.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelResponse {

    private ResponseType type;
    private String content;
    private String command;

}
