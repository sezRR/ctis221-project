package dev.sezrr.examples.llmchatservice.shared.validation;

import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.custom.ValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
       return CustomValidationException.handleValidationException(new CustomValidationException(ex.getBindingResult(), "Validation failed due to incorrect input values."));
    }
}
