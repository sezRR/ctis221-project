package dev.sezrr.llmchatwrapper.llmchatservice.shared.exception;

import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.errors.ErrorStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityNotFoundExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorStatus> handleEntityNotFoundException(EntityNotFoundException ex) {
        var error = new ErrorStatus(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                "The requested entity was not found."
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
