package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Maps just the fields we actually use. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TokenResponse(
        @JsonProperty("access_token")  String accessToken,
        @JsonProperty("id_token")      String idToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("expires_in")    long   expiresIn) {}
