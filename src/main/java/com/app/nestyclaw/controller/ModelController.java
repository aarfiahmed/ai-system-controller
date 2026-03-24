package com.app.nestyclaw.controller;

import com.app.nestyclaw.dto.UserRequestDto;
import com.app.nestyclaw.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ModelController {

    private final ModelService service;

    @Autowired
    public ModelController(ModelService service) {
        this.service = service;
    }

    @PostMapping("/process")
    public String processUserRequest(@RequestBody UserRequestDto request) throws Exception {
        log.info("received user request {}", request);

        return service.processClientMessage(request);
    }
}
