package dev.sezrr.examples.llmchatservice.shared.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
