package dev.sezrr.examples.llmchatservice.shared.customResponseEntities.errors;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ErrorDeclaration implements Serializable {
    private ErrorStatus errorStatus;
    private final String errorMessage;

    public ErrorDeclaration(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorDeclaration(ErrorStatus errorStatus, String errorMessage) {
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }
}