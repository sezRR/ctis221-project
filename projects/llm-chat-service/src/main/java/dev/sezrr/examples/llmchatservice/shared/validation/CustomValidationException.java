package dev.sezrr.examples.llmchatservice.shared.validation;

import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorStatus;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.custom.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class CustomValidationException extends RuntimeException {
    private final BindingResult bindingResult;

    public CustomValidationException(BindingResult bindingResult, String message) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public static ResponseEntity<ValidationError> handleValidationException(CustomValidationException ex) {
        // Collect validation errors
        List<ErrorDeclaration> errorDeclarations = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorDeclarations.add(new ErrorDeclaration(error.getDefaultMessage()));
        }

        // Create custom ValidationError response
        ValidationError response = new ValidationError(
                errorDeclarations,
                new ErrorStatus(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                ex.getMessage()
        );

        // Return ResponseEntity with custom response object
        return ResponseEntity.badRequest().body(response);
    }
}
