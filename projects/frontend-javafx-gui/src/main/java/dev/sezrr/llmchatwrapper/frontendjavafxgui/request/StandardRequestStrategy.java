package dev.sezrr.llmchatwrapper.frontendjavafxgui.request;

public class StandardRequestStrategy extends BaseRequestStrategy {
    public StandardRequestStrategy(String baseApiUrl) {
        super(baseApiUrl);
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
