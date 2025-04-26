package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.ConfigReader;

public class AuthConfig {
    public static final String REALM     = ConfigReader.get("google.realm");
    public static final String AUTH_URL  = ConfigReader.get("google.auth_url");
    public static final String TOKEN_URL = ConfigReader.get("google.token_url");
    public static final String CLIENT_ID = ConfigReader.get("google.client_id");
    public static final String SCOPE     = ConfigReader.get("google.scope");
}
