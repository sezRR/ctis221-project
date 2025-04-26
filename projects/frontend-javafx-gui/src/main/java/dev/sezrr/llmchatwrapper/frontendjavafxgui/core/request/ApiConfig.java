package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.config.ConfigReader;

public class ApiConfig {
    public static final String BASE_API = ConfigReader.get("backend.api.url");
}
