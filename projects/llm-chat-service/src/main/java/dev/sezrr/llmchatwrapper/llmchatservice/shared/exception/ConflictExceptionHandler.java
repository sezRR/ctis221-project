package dev.sezrr.llmchatwrapper.llmchatservice.shared.exception;

import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.errors.ErrorDeclaration;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.errors.ErrorStatus;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.errors.custom.ConflictError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConflictExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ConflictError> handleResourceAlreadyExistsException(ConflictException ex) {
        var conflictError = new ConflictError(
                new ErrorDeclaration(ex.getMessage()),
                new ErrorStatus(HttpStatus.CONFLICT.toString(), HttpStatus.CONFLICT.value()),
                "The resource you are trying to create already exists."
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictError);
    }
}
