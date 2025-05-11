package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import reactor.core.publisher.Flux;

public class ApiClient {
    private RestRequestStrategy restRequestStrategy;
    private StreamingRequestStrategy streamingRequestStrategy;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient(RestRequestStrategy restRequestStrategy) {
        this.restRequestStrategy = restRequestStrategy;
    }
    
    public ApiClient(StreamingRequestStrategy streamingRequestStrategy) {
        this.streamingRequestStrategy = streamingRequestStrategy;
    }
    
    public ApiClient(RestRequestStrategy restRequestStrategy, StreamingRequestStrategy streamingRequestStrategy) {
        this.restRequestStrategy = restRequestStrategy;
        this.streamingRequestStrategy = streamingRequestStrategy;
    }
    
    public void setStreamingRequestStrategy(StreamingRequestStrategy streamingRequestStrategy) {
        this.streamingRequestStrategy = streamingRequestStrategy;
    }
    
    public void setRestRequestStrategy(RestRequestStrategy restRequestStrategy) {
        this.restRequestStrategy = restRequestStrategy;
    }

    public String get(String endpoint) {
        return restRequestStrategy.get(endpoint);
    }

    public Flux<String> stream(String endpoint, String body) {
        if (streamingRequestStrategy == null) {
            throw new IllegalStateException("StreamingRequestStrategy is not set");
        }
        
        return streamingRequestStrategy.stream(endpoint, body);
    }
    
    public String post(String endpoint, String body) {
        return restRequestStrategy.post(endpoint, body);
    }

    public String put(String endpoint, String body) {
        return restRequestStrategy.put(endpoint, body);
    }

    public String patch(String endpoint, String body) {
        return restRequestStrategy.patch(endpoint, body);
    }

    public String delete(String endpoint) {
        return restRequestStrategy.delete(endpoint);
    }

    public <T> T get(String endpoint, TypeReference<T> typeReference) {
        String response = get(endpoint);
        return convertResponse(response, typeReference);
    }

    public <T, F> T post(String endpoint, F objectBody, TypeReference<T> typeReference) {
        String response = post(endpoint, convertObjectToStringJson(objectBody));
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
    
    public <T> T delete(String endpoint, TypeReference<T> typeReference) {
        String response = delete(endpoint);
        return convertResponse(response, typeReference);
    }
    
    private <T> String convertObjectToStringJson(T object) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    private <T> T convertResponse(String response, TypeReference<T> typeReference) {
        try {
            if (response == null || response.isEmpty())
                return null;
            System.out.println(response);
            return objectMapper.readValue(response, typeReference);
        } catch (Exception e) {
            System.out.println("Failed to parse response: " + e.getMessage());
            throw new RuntimeException("Failed to parse response", e);
        }
    }
}
