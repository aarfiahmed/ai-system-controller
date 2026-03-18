package com.app.nestyclaw.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class NestyExceptionHandler {


    @ExceptionHandler(NestyException.class)
    public ResponseEntity<Map> handleException(NestyException ex){
        log.error("exception handling", ex);
        Map<String, String> response = Map.of("message", ex.getMessage());
        return ResponseEntity.of(Optional.of(response));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map> handleException(RuntimeException ex){
        log.error("exception handling", ex);
        Map<String, String> response = Map.of("message", ex.getMessage());
        return ResponseEntity.of(Optional.of(response));
    }
}
