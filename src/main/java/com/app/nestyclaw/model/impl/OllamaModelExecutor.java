package com.app.nestyclaw.model.impl;

import com.app.nestyclaw.dto.ChatMessage;
import com.app.nestyclaw.dto.ModelRequest;
import com.app.nestyclaw.dto.RoleType;
import com.app.nestyclaw.model.ModelExecutor;
import com.app.nestyclaw.model.promptbuilder.AgentPromptBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OllamaModelExecutor implements ModelExecutor {

    @Value("${ollama.url}")
    String url;

    RestTemplate restTemplate = new RestTemplate();


    public String callModel(String query) throws JsonProcessingException {
        ModelRequest req = new ModelRequest();
        req.setMessages(List.of(new ChatMessage(RoleType.SYSTEM, AgentPromptBuilder.getSystemPrompt()), new ChatMessage(RoleType.USER, query)));
        req.setModel("gpt-oss:20b");

        log.info("prompt -> {}", new ObjectMapper().writeValueAsString(req));

        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:11434/api/chat", req, Map.class);
        log.info("response -> {}", response);
        Map<String, Object> messageMap = (Map<String, Object>) response.getBody().get("message");
        String result = (String) messageMap.get("content");

        log.info("Result → {}", result);
        log.info(result);
        return result;
    }

/*
    public String callModel1(String prompt) {
        ModelRequest req = new ModelRequest();
        req.setModel("gpt-oss:20b");
        //  req.setFormat("json");
        req.setPrompt(prompt);
        req.setStream(false);
        //  req.setTemperature();

        log.info("user prompt {}", prompt);
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-oss:20b");
        body.put("prompt", prompt);
        body.put("format", "json");
        body.put("temperature", 0);
        body.put("stream", false);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, body, Map.class);
        log.info("response from model {}", response);
        return response.getBody().get("response").toString();
    }*/
}
