package com.app.nestyclaw.model.promptbuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AgentPromptBuilder {

    public String build(String userMessage) {

        return """
                You are a system assistant.

                Your job:
                If user is asking for information → return TEXT.
                If user is asking to execute something on machine → return COMMAND.

                STRICT RULES:
                - Return ONLY valid JSON.
                - Do NOT add explanation.
                - Do NOT add markdown.
                - No extra text.

                Output format:

                {
                  "type": "TEXT" or "COMMAND",
                  "content": "string if TEXT",
                  "command": "string if COMMAND"
                }

                User message:
                """ + userMessage;
    }
}
