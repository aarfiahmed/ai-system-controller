package com.app.nestyclaw.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelRequest {
    String model;
    String prompt;
    String format;
    String temperature;
    boolean stream;
}
