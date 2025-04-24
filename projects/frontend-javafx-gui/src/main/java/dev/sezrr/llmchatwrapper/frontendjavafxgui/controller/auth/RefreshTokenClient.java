package dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

final class RefreshTokenClient {
    static TokenResponse refresh(String refreshToken, String tokenUrl, String clientId) throws Exception {
        String body = "grant_type=refresh_token" +
                "&client_id=" + url(clientId) +
                "&refresh_token=" + url(refreshToken);

        HttpRequest req = HttpRequest.newBuilder(URI.create(tokenUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> resp = HttpClient.newHttpClient()
                .send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200)
            throw new RuntimeException("Refresh failed: " + resp.body());

        return AuthApplication.MAPPER.readValue(resp.body(), TokenResponse.class);
    }

    private static String url(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
