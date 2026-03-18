package com.app.nestyclaw.model.impl;

import com.app.nestyclaw.dto.ModelRequest;
import com.app.nestyclaw.model.ModelExecutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class OllamaModelExecutor implements ModelExecutor {

    @Value("${ollama.url}")
    String url;

    RestTemplate restTemplate = new RestTemplate();



    public String callModel(String query) throws JsonProcessingException {
        ModelRequest req= new ModelRequest();
        req.setModel("gpt-oss:20b");
      //  req.setFormat("json");
        req.setPrompt(query);

        log.info("prompt -> {}",new ObjectMapper().writeValueAsString(req));

        ResponseEntity<Map> response = restTemplate.postForEntity(url, req, Map.class);
        log.info("response -> {}",response);
        String result = (String) response.getBody().get("response");
        log.info(result);
        return result;
    }


    public String callModel1(String prompt) {
        ModelRequest req= new ModelRequest();
        req.setModel("gpt-oss:20b");
        //  req.setFormat("json");
        req.setPrompt(prompt);
        req.setStream(false);
        req.setTemperature();

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
    }
}
