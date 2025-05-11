package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request;

import reactor.core.publisher.Flux;

public interface StreamingRequestStrategy extends RequestStrategy {
    Flux<String> stream(String endpoint, String body);
}
