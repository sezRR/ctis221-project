package dev.sezrr.examples.llmchatservice.shared.customresponseentities.errors.custom;

import dev.sezrr.examples.llmchatservice.shared.customresponseentities.errors.Error;
import dev.sezrr.examples.llmchatservice.shared.customresponseentities.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.customresponseentities.errors.ErrorStatus;

public class ConflictError extends Error {
    public ConflictError(ErrorDeclaration errorDeclaration, ErrorStatus errorStatus, String systemMessage) {
        super(errorDeclaration, errorStatus, systemMessage);
    }
}
