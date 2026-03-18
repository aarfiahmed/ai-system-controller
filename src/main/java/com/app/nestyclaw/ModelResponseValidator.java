package com.app.nestyclaw;

import com.app.nestyclaw.dto.ModelResponse;
import com.app.nestyclaw.dto.ResponseType;
import com.app.nestyclaw.error.NestyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ModelResponseValidator {

public void validate(ModelResponse response){

    if(response.getType() == null){
        throw new NestyException("model response does not contain type");
    }
    else if( response.getType() == ResponseType.COMMAND && StringUtils.isEmpty(response.getCommand())){
        throw new NestyException("model response type is COMMAND but there is command value ");
    }
    else if( response.getType() == ResponseType.TEXT && StringUtils.isEmpty(response.getContent())){
        throw new NestyException("model response type is TEXTe but there is content value ");
    }
}
}
