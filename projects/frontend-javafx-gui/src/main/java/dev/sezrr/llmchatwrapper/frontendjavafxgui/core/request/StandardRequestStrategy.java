package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request;

public class StandardRequestStrategy extends BaseRequestStrategy {
    public StandardRequestStrategy(String baseApiUrl) {
        super(baseApiUrl);
    }

    @Override
    public void validateRequest(String endpoint, String body) throws Exception {
        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("Request body cannot be null or empty");
        }
    }
    
    @Override
    public String get(String endpoint) {
        return sendRequest(HttpMethod.GET, endpoint, null);
    }

    @Override
    public String post(String endpoint, String body) {
        return sendRequest(HttpMethod.POST, endpoint, body);
    }

    @Override
    public String put(String endpoint, String body) {
        return sendRequest(HttpMethod.PUT, endpoint, body);
    }

    @Override
    public String patch(String endpoint, String body) {
        return sendRequest(HttpMethod.PATCH, endpoint, body);
    }

    @Override
    public String delete(String endpoint) {
        return sendRequest(HttpMethod.DELETE, endpoint, null);
    }
}
