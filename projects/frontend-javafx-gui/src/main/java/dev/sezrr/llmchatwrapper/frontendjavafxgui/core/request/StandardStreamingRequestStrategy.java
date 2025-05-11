package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class StandardStreamingRequestStrategy implements StreamingRequestStrategy {
    private final HttpClient httpClient;
    private final String baseApi;
    
    public StandardStreamingRequestStrategy(String baseApi) {
        this.httpClient = HttpClient.newHttpClient();
        this.baseApi = baseApi;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Flux<String> stream(String endpoint, String body) {
        return Flux.create(emitter -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(this.baseApi + endpoint))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                        .thenAccept(response -> {
                            try (BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(response.body(), StandardCharsets.UTF_8))) {
                                String line;
                                StringBuilder dataBuilder = new StringBuilder();

                                while ((line = reader.readLine()) != null) {
                                    if (line.isEmpty()) {
                                        String eventData = dataBuilder.toString().trim();
                                        dataBuilder.setLength(0);
                                        if (eventData.isEmpty()) continue;

                                        if (eventData.equals("[DONE]")) {
                                            emitter.complete();
                                            return;
                                        }

                                        // Parse JSON and extract the 'text'
                                        try {
                                            // Just forward the plain token
                                            emitter.next(eventData);
                                        } catch (Exception ex) {
                                            System.err.println("Invalid JSON chunk: " + ex.getMessage());
                                        }
                                    } else if (line.startsWith("data:")) {
                                        dataBuilder.append(line.substring(5)).append("\n");
                                    }
                                }

                                emitter.complete();
                            } catch (Exception e) {
                                emitter.error(e);
                            }
                        })
                        .exceptionally(e -> {
                            emitter.error(e);
                            return null;
                        });
            } catch (Exception e) {
                emitter.error(e);
            }
        }, FluxSink.OverflowStrategy.BUFFER);
    }
}
