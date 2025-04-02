package dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.custom;

import dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.Error;
import dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.ErrorStatus;

public class ConflictError extends Error {
    public ConflictError(ErrorDeclaration errorDeclaration, ErrorStatus errorStatus, String systemMessage) {
        super(errorDeclaration, errorStatus, systemMessage);
    }
}
