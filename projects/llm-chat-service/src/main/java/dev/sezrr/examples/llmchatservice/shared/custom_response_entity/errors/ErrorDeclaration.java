package dev.sezrr.examples.llmchatservice.shared.custom_response_entity.errors;

import java.io.Serializable;

public record ErrorDeclaration(String errorMessage) implements Serializable {
}