package com.app.nestyclaw.executor;

import com.app.nestyclaw.dto.ModelResponse;
import com.app.nestyclaw.validator.ModelResponseSanitizer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandExecutor {

    private final ModelResponseSanitizer modelResponseSanitizer;


    public boolean executeCommand(ModelResponse response) {
        modelResponseSanitizer.sanitizeCommand(response.getContent());

        return true;
    }
}
