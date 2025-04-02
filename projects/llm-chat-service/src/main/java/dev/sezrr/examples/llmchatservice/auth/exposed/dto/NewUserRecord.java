package dev.sezrr.examples.llmchatservice.auth.exposed.dto;

public record NewUserRecord(String username, String password, String firstName, String lastName, String email) {
}
