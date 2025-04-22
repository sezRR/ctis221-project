package dev.sezrr.llmchatwrapper.frontendjavafxgui.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiClient {
    private final String baseApiUrl;
    private RequestStrategy requestStrategy;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient(String baseApiUrl, RequestStrategy requestStrategy) {
        this.baseApiUrl = baseApiUrl;
        this.requestStrategy = requestStrategy;
    }

    public void setStrategy(RequestStrategy requestStrategy) {
        this.requestStrategy = requestStrategy;
    }

    public String get(String endpoint) {
        return requestStrategy.get(baseApiUrl + endpoint);
    }

    public String post(String endpoint, String body) {
        return requestStrategy.post(baseApiUrl + endpoint, body);
    }

    public String put(String endpoint, String body) {
        return requestStrategy.put(baseApiUrl + endpoint, body);
    }

    public String patch(String endpoint, String body) {
        return requestStrategy.patch(baseApiUrl + endpoint, body);
    }

    public String delete(String endpoint) {
        return requestStrategy.delete(baseApiUrl + endpoint);
    }

    public <T> T get(String endpoint, TypeReference<T> typeReference) {
        String response = get(endpoint);
        return convertResponse(response, typeReference);
    }

    public <T> T post(String endpoint, String body, TypeReference<T> typeReference) {
        String response = post(endpoint, body);
        return convertResponse(response, typeReference);
    }

    public <T> T put(String endpoint, String body, TypeReference<T> typeReference) {
        String response = put(endpoint, body);
        return convertResponse(response, typeReference);
    }

    public <T> T patch(String endpoint, String body, TypeReference<T> typeReference) {
        String response = patch(endpoint, body);
        return convertResponse(response, typeReference);
    }

    private <T> T convertResponse(String response, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(response, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}
