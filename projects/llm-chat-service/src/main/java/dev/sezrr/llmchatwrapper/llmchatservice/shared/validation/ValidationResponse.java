package dev.sezrr.llmchatwrapper.llmchatservice.shared.validation;

import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse {
    private final boolean isValid;
    private final List<ObjectError> errors;
    
    public ValidationResponse(boolean isValid) {
        this.isValid = isValid;
        this.errors = new ArrayList<>();
    }
    
    public ValidationResponse(boolean isValid, List<ObjectError> errors) {
        this.isValid = isValid;
        this.errors = errors;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<ObjectError> getMessages() {
        return errors;
    }
}
