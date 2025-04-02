package dev.sezrr.examples.llmchatservice.auth.exposed.contract;

import dev.sezrr.examples.llmchatservice.auth.exposed.dto.NewUserRecord;

public interface UserService {
    void createUser(NewUserRecord newUserRecord);
    void sendVerificationEmail(String userId);
}
