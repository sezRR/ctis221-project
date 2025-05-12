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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StandardStreamingRequestStrategy implements StreamingRequestStrategy {
    private final HttpClient httpClient;
    private final String baseApi;
    
    public StandardStreamingRequestStrategy(String baseApi) {
        this.httpClient = HttpClient.newHttpClient();
        this.baseApi = baseApi;
    }

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

                                List<String> dataLines = new ArrayList<>();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    if (line.startsWith("data:")) {
                                        // strip off "data:" prefix (preserves empty lines too)
                                        dataLines.add(line.substring(5));
                                    }
                                    else if (line.isEmpty()) {
                                        // end of one SSE event
                                        String rawData = String.join("\n", dataLines);
                                        dataLines.clear();

                                        if ("[DONE]".equals(rawData.trim())) {
                                            emitter.complete();
                                            return;
                                        }

                                        emitter.next(rawData);
                                    }
                                }

                                emitter.complete();
                            }
                            catch (Exception e) {
                                emitter.error(e);
                            }
                        })
                        .exceptionally(e -> { emitter.error(e); return null; });
            } catch (Exception e) {
                emitter.error(e);
            }
        }, FluxSink.OverflowStrategy.BUFFER);
    }

}
