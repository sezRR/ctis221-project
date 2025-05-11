package dev.sezrr.llmchatwrapper.llmchatservice.shared.validation;

import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.errors.custom.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomValidationExceptionHandler {
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ValidationError> handleCustomValidationException(CustomValidationException ex) {
        return CustomValidationException.handleValidationException(ex);
    }
}
