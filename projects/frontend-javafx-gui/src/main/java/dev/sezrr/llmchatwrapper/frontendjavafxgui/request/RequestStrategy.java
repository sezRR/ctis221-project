package dev.sezrr.llmchatwrapper.frontendjavafxgui.request;

public interface RequestStrategy {
    String get(String endpoint);
    String post(String endpoint, String body);
    String put(String endpoint, String body);
    String patch(String endpoint, String body);
    String delete(String endpoint);
}
