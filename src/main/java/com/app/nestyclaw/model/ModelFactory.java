package com.app.nestyclaw.model;

import com.app.nestyclaw.model.impl.OllamaModelExecutor;
import com.app.nestyclaw.model.impl.OpenAiModelExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ModelFactory {

    OllamaModelExecutor ollamaModelExecutor;
    OpenAiModelExecutor openAiModelExecutor;
    ModelType modelType;

    @Autowired
    public ModelFactory(OllamaModelExecutor ollamaModelExecutor, OpenAiModelExecutor openAiModelExecutor,   @Value("${model.type:ollama}") ModelType modelType) {
        this.ollamaModelExecutor = ollamaModelExecutor;
        this.openAiModelExecutor = openAiModelExecutor;
        this.modelType = modelType;
    }


    public ModelExecutor getModel() {
        return switch (modelType) {
            case OLLAMA -> ollamaModelExecutor;
            case OPENAI -> openAiModelExecutor;
            default -> ollamaModelExecutor;
        };
    }
}
