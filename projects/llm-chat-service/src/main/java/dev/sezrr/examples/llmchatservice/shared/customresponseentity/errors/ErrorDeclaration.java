package dev.sezrr.examples.llmchatservice.shared.customresponseentity.errors;

import java.io.Serializable;

public record ErrorDeclaration(String errorMessage) implements Serializable {
}