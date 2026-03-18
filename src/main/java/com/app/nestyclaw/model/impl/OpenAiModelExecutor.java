package com.app.nestyclaw.model.impl;

import com.app.nestyclaw.model.ModelExecutor;
import org.springframework.stereotype.Component;

@Component
public class OpenAiModelExecutor implements ModelExecutor {

    @Override
    public String callModel(String query) {
        return "";
    }
}
