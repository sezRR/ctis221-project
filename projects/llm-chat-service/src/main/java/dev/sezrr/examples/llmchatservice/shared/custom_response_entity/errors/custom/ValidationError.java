package dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.custom;

import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.Error;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorStatus;

import java.util.List;

public class ValidationError extends Error {
    public ValidationError(List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus, String systemMessage) {
        super(errorDeclarations, errorStatus, systemMessage);
    }
}
