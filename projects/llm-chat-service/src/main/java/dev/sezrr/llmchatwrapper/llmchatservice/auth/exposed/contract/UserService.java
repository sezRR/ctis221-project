package dev.sezrr.llmchatwrapper.llmchatservice.auth.exposed.contract;

import dev.sezrr.llmchatwrapper.llmchatservice.auth.exposed.dto.NewUserRecord;

public interface UserService {
    void createUser(NewUserRecord newUserRecord);
    void sendVerificationEmail(String userId);
}
