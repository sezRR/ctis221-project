package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request;

import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public abstract class BaseRestRequestStrategy implements RestRequestStrategy {
    protected String baseApiUrl;
    protected final HttpClient httpClient = HttpClient.newHttpClient();
    private String authToken;
    
    public BaseRestRequestStrategy(String baseApiUrl) {
        this.baseApiUrl = baseApiUrl;
    }
    
    protected String buildUrl(String endpoint) {
        return baseApiUrl + endpoint;
    }
    
    protected String sendRequest(HttpMethod method, String endpoint, String body) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(buildUrl(endpoint)))
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json");

            switch (method) {
                case GET:
                    requestBuilder.GET();
                    break;
                case POST:
                    requestBuilder.POST(BodyPublishers.ofString(body));
                    break;
                case PUT:
                    requestBuilder.PUT(BodyPublishers.ofString(body));
                    break;
                case PATCH:
                    requestBuilder.method("PATCH", BodyPublishers.ofString(body));
                    break;
                case DELETE:
                    requestBuilder.DELETE();
                    break;
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            
            return response.body();
        } catch (Exception e) {
            if (e instanceof ConnectException || e.getMessage().contains("Connection refused") || e instanceof HttpConnectTimeoutException)
                throw new RuntimeException("ConnectionError: " + "Server (backend) is not reachable");
            
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
     public void setAuthToken(String authToken) {
        this.authToken = authToken;
     }
     
     // Abstract method to be implemented by subclasses
    public abstract void validateRequest(String endpoint, String body) throws Exception;
}

