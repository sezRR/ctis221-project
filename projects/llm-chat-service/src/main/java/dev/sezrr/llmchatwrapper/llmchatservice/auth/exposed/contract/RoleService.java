package dev.sezrr.llmchatwrapper.llmchatservice.auth.exposed.contract;

public interface RoleService {
    void assignRole(String userId, String role);
}
