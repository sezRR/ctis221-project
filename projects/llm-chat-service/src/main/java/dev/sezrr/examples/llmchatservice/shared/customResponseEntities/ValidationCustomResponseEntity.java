package dev.sezrr.examples.llmchatservice.shared.customResponseEntities;

import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.errors.ErrorStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationCustomResponseEntity extends BaseCustomResponseEntity {
    private final List<ErrorDeclaration> errorDeclarations;
    private final ErrorStatus errorStatus;
    private final String systemMessage;

    public ValidationCustomResponseEntity(String systemMessage, List<ErrorDeclaration> errorDeclarations, String message, ErrorStatus errorStatus) {
        super(message, false);
        this.errorDeclarations = errorDeclarations;
        this.systemMessage = systemMessage;
        this.errorStatus = errorStatus;
    }

    public ValidationCustomResponseEntity(String systemMessage, List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus) {
        super(false);
        this.errorDeclarations = errorDeclarations;
        this.systemMessage = systemMessage;
        this.errorStatus = errorStatus;
    }
}
