package com.app.nestyclaw.validator;

import com.app.nestyclaw.dto.ModelResponse;
import com.app.nestyclaw.dto.ResponseType;
import com.app.nestyclaw.error.NestyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ModelResponseValidator {

    public void validate(ModelResponse response) {

        if (response.getType() == null) {
            throw new NestyException("model response does not contain type");
        } else if (StringUtils.isEmpty(response.getContent())) {
            throw new NestyException("model response content is blank ");
        } else if (!(response.getType() == ResponseType.COMMAND || response.getType() == ResponseType.TEXT)) {
            throw new NestyException("model response type is not of type COMMAND or TEXT ");
        }
    }
}
