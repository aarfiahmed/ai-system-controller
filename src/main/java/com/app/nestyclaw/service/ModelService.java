package com.app.nestyclaw.service;

import com.app.nestyclaw.ModelResponseValidator;
import com.app.nestyclaw.dto.ModelResponse;
import com.app.nestyclaw.dto.UserRequestDto;
import com.app.nestyclaw.model.ModelExecutor;
import com.app.nestyclaw.model.ModelFactory;
import com.app.nestyclaw.model.promptbuilder.AgentPromptBuilder;
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


    @Autowired
    public ModelService(ModelFactory modelFactory, ModelResponseValidator modelResponseValidator) {
        modelExecutor = modelFactory.getModel();
        this.objectMapper = new ObjectMapper();
        this.validator= modelResponseValidator;
    }


    public String processClientMessage(UserRequestDto clientMessage) throws Exception {
        String result = modelExecutor.callModel(AgentPromptBuilder.build(clientMessage.getMessage()));
       // String result = modelExecutor.callModel(clientMessage.getMessage());

        ModelResponse modelResponse = objectMapper.readValue(result, ModelResponse.class);

        validator.validate(modelResponse);
        log.info("model response {}", result);
        return result;
    }


}
