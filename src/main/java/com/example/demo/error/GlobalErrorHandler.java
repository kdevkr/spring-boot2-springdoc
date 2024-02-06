package com.example.demo.error;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Component
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error("{}", e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse()
            .setErrorCode("500")
            .setMessage(e.getMessage())
            .setTimestamp(Instant.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse()
            .setErrorCode("403")
            .setMessage(e.getMessage())
            .setTimestamp(Instant.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
