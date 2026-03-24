package com.app.nestyclaw.service;

import com.app.nestyclaw.dto.ModelResponse;
import com.app.nestyclaw.dto.UserRequestDto;
import com.app.nestyclaw.model.ModelExecutor;
import com.app.nestyclaw.model.ModelFactory;
import com.app.nestyclaw.validator.ModelResponseSanitizer;
import com.app.nestyclaw.validator.ModelResponseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class ModelService {

    private final ModelExecutor modelExecutor;
    private final ObjectMapper objectMapper;
    private final ModelResponseValidator validator;
    private final ModelResponseSanitizer modelResponseSanitizer;

    @Autowired
    public ModelService(ModelFactory modelFactory, ModelResponseValidator modelResponseValidator, ModelResponseSanitizer modelResponseSanitizer) {
        this.modelExecutor = modelFactory.getModel();
        this.objectMapper = new ObjectMapper();
        this.validator = modelResponseValidator;
        this.modelResponseSanitizer = modelResponseSanitizer;
    }

    public String processClientMessage(UserRequestDto clientMessage) throws Exception {
        String result = modelExecutor.callModel(clientMessage.getMessage());

        ModelResponse modelResponse = objectMapper.readValue(result, ModelResponse.class);

        validator.validate(modelResponse);
        modelResponseSanitizer.sanitizeCommand(modelResponse.getContent());
        log.info("model response {}", result);
        return result;
    }
}
