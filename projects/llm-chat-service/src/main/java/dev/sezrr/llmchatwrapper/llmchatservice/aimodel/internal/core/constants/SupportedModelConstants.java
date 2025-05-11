package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.core.constants;

public final class SupportedModelConstants {
    private SupportedModelConstants() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("Constants util class cannot be instantiated");
    }
    
    public static final String SUPPORTED_MODEL_CACHE_NAME = "supportedModels";
    public static final String VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED = "Model name is required";
    public static final String VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED = "API URL is required";
}
