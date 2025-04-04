package dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.custom;

import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.Error;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors.ErrorStatus;

public class ConflictError extends Error {
    public ConflictError(ErrorDeclaration errorDeclaration, ErrorStatus errorStatus, String systemMessage) {
        super(errorDeclaration, errorStatus, systemMessage);
    }
}
