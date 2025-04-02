package dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors;

import java.util.Collections;
import java.util.List;

public abstract class Error {
    private final List<ErrorDeclaration> errorDeclarations;
    private final ErrorStatus errorStatus;
    private final String systemMessage;

    public Error(List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus, String systemMessage) {
        this.errorDeclarations = errorDeclarations;
        this.errorStatus = errorStatus;
        this.systemMessage = systemMessage;
    }

    public Error(List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus) {
        this(errorDeclarations, errorStatus, null);
    }

    public Error(ErrorDeclaration errorDeclaration, ErrorStatus errorStatus, String systemMessage) {
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
