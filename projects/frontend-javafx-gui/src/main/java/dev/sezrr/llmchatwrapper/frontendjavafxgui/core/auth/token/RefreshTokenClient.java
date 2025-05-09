package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.AuthService;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public final class RefreshTokenClient {
    public static TokenResponse refresh(String refreshToken, String tokenUrl, String clientId) throws Exception {
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

        return AuthService.getMapper().readValue(resp.body(), TokenResponse.class);
    }

    public static String url(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
