package dev.sezrr.examples.llmchatservice.shared.customresponseentities.errors;

import java.io.Serializable;

public record ErrorDeclaration(String errorMessage) implements Serializable {
}