package dev.sezrr.examples.llmchatservice.auth.exposed.contract;

import dev.sezrr.examples.llmchatservice.auth.exposed.dto.NewUserRecord;

public interface RoleService {
    void assignRole(String userId, String role);
}
