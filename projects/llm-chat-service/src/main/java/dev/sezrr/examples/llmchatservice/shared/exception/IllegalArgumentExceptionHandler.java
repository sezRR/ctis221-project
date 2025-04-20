package dev.sezrr.examples.llmchatservice.shared.exception;

import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorStatus> handleIllegalArgumentException(IllegalArgumentException ex) {
        var error = new ErrorStatus(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "The request contains an illegal argument."
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
