package dev.sezrr.examples.llmchatservice.aimodel.internal.core.exceptions.handlers;

import dev.sezrr.examples.llmchatservice.shared.validation.CustomValidationException;
import dev.sezrr.examples.llmchatservice.shared.customresponseentities.errors.custom.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomValidationExceptionHandler {
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ValidationError> handleValidation(CustomValidationException ex) {
        return CustomValidationException.handleValidationException(ex);
    }
}
