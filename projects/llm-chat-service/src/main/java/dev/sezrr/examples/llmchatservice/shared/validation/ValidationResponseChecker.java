package dev.sezrr.examples.llmchatservice.shared.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationResponseChecker {
    private final BeanValidation beanValidation;

    @Autowired
    public ValidationResponseChecker(BeanValidation beanValidation) {
        this.beanValidation = beanValidation;
    }

    public <T> ValidationResponse validateObject(T object){
        var bindingResult = beanValidation.validate(object);

        if (bindingResult.hasErrors())
            return new ValidationResponse(false, bindingResult.getAllErrors());
        
        return new ValidationResponse(true);
    }
}