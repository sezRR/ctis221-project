package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.JwtUtils;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.Pkce;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.RefreshTokenClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenResponse;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenStore;
import javafx.application.HostServices;
import javafx.application.Platform;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class AuthService {
    private static final String REALM     = "llm-chat-wrapper";
    private static final String AUTH_URL  = "http://localhost:9082/realms/" + REALM + "/protocol/openid-connect/auth";
    private static final String TOKEN_URL = "http://localhost:9082/realms/" + REALM + "/protocol/openid-connect/token";
    private static final String CLIENT_ID = "desktop-app";
    private static final String SCOPE     = "openid email profile offline_access";

    private final HostServices hostServices;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthService(HostServices hostServices) {
        this.hostServices = hostServices;
    }
    
    public AuthService(HostServices hostServices, Consumer<String> onSuccess) {
        this.hostServices = hostServices;
        trySilentLogin(onSuccess);
    }

    public void startAuthFlow(Consumer<String> onSuccess) {
        if (!trySilentLogin(onSuccess)) {
            try {
                beginOAuthFlow(onSuccess);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean trySilentLogin(Consumer<String> onSuccess) {
        return TokenStore.load().map(stored -> {
            long now = System.currentTimeMillis() / 1000;
            if (now < JwtUtils.expires(stored.idToken())) {
                Platform.runLater(() -> onSuccess.accept(JwtUtils.preferredUsername(stored.idToken())));
                return true;
            }
            try {
                TokenResponse fresh = RefreshTokenClient.refresh(stored.refreshToken(), TOKEN_URL, CLIENT_ID);
                TokenStore.save(fresh);
                Platform.runLater(() -> onSuccess.accept(JwtUtils.preferredUsername(fresh.idToken())));
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }).orElse(false);
    }

    private void beginOAuthFlow(Consumer<String> onSuccess) throws IOException {
        int port = findFreePort();
        String redirectUri = "http://127.0.0.1:" + port + "/cb";

        String codeVerifier  = Pkce.createVerifier();
        String codeChallenge = Pkce.challengeS256(codeVerifier);
        String state         = UUID.randomUUID().toString();

        String authUrl = AUTH_URL +
                "?client_id=" + url(CLIENT_ID) +
                "&response_type=code" +
                "&redirect_uri=" + url(redirectUri) +
                "&scope=" + url(SCOPE) +
                "&code_challenge=" + url(codeChallenge) +
                "&code_challenge_method=S256" +
                "&state=" + url(state) +
                "&kc_idp_hint=google";

        hostServices.showDocument(authUrl);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/cb", exchange -> {
            Map<String,String> params = parseQuery(exchange.getRequestURI().getRawQuery());
            if (!state.equals(params.get("state"))) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }
            TokenResponse tok = exchangeForTokens(params.get("code"), redirectUri, codeVerifier);
            TokenStore.save(tok);

            String cookie = "id_token=" + URLEncoder.encode(tok.idToken(), StandardCharsets.UTF_8) +
                    "; Max-Age=" + tok.expiresIn() +
                    "; Path=/; HttpOnly; SameSite=Lax";
            exchange.getResponseHeaders().add("Set-Cookie", cookie);

            byte[] body = "<html><body><h2>You may return to the app.</h2></body></html>"
                    .getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
            server.stop(0);

            String user = JwtUtils.preferredUsername(tok.idToken());
            Platform.runLater(() -> onSuccess.accept(user));
        });
        server.start();
    }

    private static int findFreePort() throws IOException {
        try (ServerSocket s = new ServerSocket(0)) {
            return s.getLocalPort();
        }
    }

    private static Map<String,String> parseQuery(String raw) throws IOException {
        Map<String,String> map = new HashMap<>();
        if (raw == null || raw.isEmpty()) return map;
        for (String pair : raw.split("&")) {
            int eq = pair.indexOf('=');
            String k = URLDecoder.decode(eq > 0 ? pair.substring(0, eq) : pair, StandardCharsets.UTF_8);
            String v = eq > 0 && eq < pair.length() - 1
                    ? URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8)
                    : "";
            map.put(k, v);
        }
        return map;
    }

    private TokenResponse exchangeForTokens(String code, String redirectUri, String codeVerifier) {
        String body = "grant_type=authorization_code" +
                "&client_id=" + url(CLIENT_ID) +
                "&code=" + url(code) +
                "&redirect_uri=" + url(redirectUri) +
                "&code_verifier=" + url(codeVerifier);

        HttpRequest req = HttpRequest.newBuilder(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> resp = HttpClient.newHttpClient()
                    .send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                throw new RuntimeException("Token endpoint error: " + resp.body());
            }
            return mapper.readValue(resp.body(), TokenResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String url(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}