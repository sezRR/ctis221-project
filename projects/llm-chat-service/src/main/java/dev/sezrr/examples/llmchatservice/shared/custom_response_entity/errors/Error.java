package dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors;

import java.util.Collections;
import java.util.List;

public abstract class Error {
    private final List<ErrorDeclaration> errorDeclarations;
    private final ErrorStatus errorStatus;
    private final String systemMessage;

    protected Error(List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus, String systemMessage) {
        this.errorDeclarations = errorDeclarations;
        this.errorStatus = errorStatus;
        this.systemMessage = systemMessage;
    }

    protected Error(List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus) {
        this(errorDeclarations, errorStatus, null);
    }

    protected Error(ErrorDeclaration errorDeclaration, ErrorStatus errorStatus, String systemMessage) {
        this(Collections.singletonList(errorDeclaration), errorStatus, systemMessage);
    }
    
    public List<ErrorDeclaration> getErrorDeclarations() {
        return errorDeclarations;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }

    public String getSystemMessage() {
        return systemMessage;
    }
}
