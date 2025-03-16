package dev.sezrr.examples.llmchatservice.modules.aimodel.core.exceptions;

import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.ValidationCustomResponseEntity;
import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.errors.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationCustomResponseEntity> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Collect validation errors
        List<ErrorDeclaration> errorDeclarations = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorDeclarations.add(new ErrorDeclaration(error.getDefaultMessage()));
        }

        // Create custom ValidationResponseEntity
        ValidationCustomResponseEntity response = new ValidationCustomResponseEntity(
                "Validation failed due to incorrect input values",
                errorDeclarations,
                new ErrorStatus(HttpStatus.BAD_REQUEST.value(), "Validation")
        );

        // Return ResponseEntity with custom response object
        return ResponseEntity.badRequest().body(response);
    }
}
