package com.app.nestyclaw.validator;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ModelResponseSanitizer {

    public String sanitizeCommand(String command) {
        if (command == null || command.isBlank()) return command;

        // Step 1: Unescape backslash-quotes → \"  to  "
        command = command.replace("\\\"", "\"");

        // Step 2: Unnecessary quotes remove karo simple filenames se
        // "my.txt" → my.txt  (only if no spaces inside)
        command = removeUnnecessaryQuotes(command);

        // Step 3: Extra whitespace clean karo
        command = command.trim().replaceAll(" +", " ");

        log.info("Sanitized command → {}", command);
        return command;
    }

    private String removeUnnecessaryQuotes(String command) {
        // Regex: "word" → word  (sirf agar quotes ke andar spaces nahi hain)
        return command.replaceAll("\"([^\"\\s]+)\"", "$1");
    }

}
