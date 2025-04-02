package dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.custom;

import dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.Error;
import dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.ErrorDeclaration;
import dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors.ErrorStatus;

import java.util.List;

public class ValidationError extends Error {
    public ValidationError(List<ErrorDeclaration> errorDeclarations, ErrorStatus errorStatus, String systemMessage) {
        super(errorDeclarations, errorStatus, systemMessage);
    }
}
