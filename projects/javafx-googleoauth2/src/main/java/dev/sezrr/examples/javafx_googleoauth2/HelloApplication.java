package dev.sezrr.examples.javafx_googleoauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.net.URLDecoder;

public class HelloApplication extends Application {

    /* ---------- Keycloak / Google config ---------------------------------- */
    private static final String REALM     = "llm-chat-wrapper";
    private static final String AUTH_URL  =
            "http://localhost:9082/realms/" + REALM + "/protocol/openid-connect/auth";
    private static final String TOKEN_URL =
            "http://localhost:9082/realms/" + REALM + "/protocol/openid-connect/token";
    private static final String CLIENT_ID = "desktop-app";
    private static final String SCOPE     = "openid email profile offline_access";
    /* ---------------------------------------------------------------------- */

    private HelloController controller;

    static final ObjectMapper MAPPER = new ObjectMapper();   // shared

    @Override public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        controller  = loader.getController();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("OAuth 2 – JavaFX");
        stage.show();

        /* ──► NEW: try silent login first */
        if (!trySilentLogin()) {
            beginOAuthFlow();             // fall‑back to interactive
        }
    }

    /* -------------------------- OAuth flow -------------------------------- */
    private void beginOAuthFlow() throws IOException {
        int port           = findFreePort();
        String redirectUri = "http://127.0.0.1:" + port + "/cb";

        String codeVerifier  = Pkce.createVerifier();
        String codeChallenge = Pkce.challengeS256(codeVerifier);
        String state         = UUID.randomUUID().toString();

        String authUrl = buildAuthUrl(redirectUri, codeChallenge, state);

        // 1. open the user’s browser
        getHostServices().showDocument(authUrl);

        // 2. start a tiny HTTP server to catch the redirect
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/cb", exchange -> {
            Map<String,String> p = parseQuery(exchange.getRequestURI().getRawQuery());
            if (!state.equals(p.get("state"))) { exchange.sendResponseHeaders(400, -1); return; }

            /* 1️⃣  Exchange code → tokens BEFORE replying, so we can set the cookie */
            TokenResponse tok = exchangeForTokens(p.get("code"), redirectUri, codeVerifier);
            TokenStore.save(tok);                 // persist for next launch

            /* 2️⃣  Build the Set‑Cookie header (HttpOnly, SameSite=Lax) */
            String cookie = "id_token=" + URLEncoder.encode(tok.idToken(), StandardCharsets.UTF_8) +
                    "; Max-Age=" + tok.expiresIn() +
                    "; Path=/" +
                    "; HttpOnly" +
                    "; SameSite=Lax";
            exchange.getResponseHeaders().add("Set-Cookie", cookie);

            /* 3️⃣  Friendly HTML for the browser tab             */
            byte[] body = "<html><body><h2>You may return to the app.</h2></body></html>"
                    .getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(body); }
            server.stop(0);

            /* 4️⃣  Update JavaFX UI (we still have the tokens in memory) */
            String user = JwtUtils.preferredUsername(tok.idToken());
            Platform.runLater(() -> controller.setAuthSuccess("Welcome " + user + "!"));
        });
        server.start();
    }

    /* ---------------------- helper utilities ------------------------------ */

    private static int findFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    private static Map<String,String> parseQuery(String raw) {
        Map<String,String> map = new HashMap<>();
        if (raw == null || raw.isEmpty()) return map;
        for (String pair : raw.split("&")) {
            int eq = pair.indexOf('=');
            String k = URLDecoder.decode(eq > 0 ? pair.substring(0, eq) : pair,
                    StandardCharsets.UTF_8);
            String v = eq > 0 && eq < pair.length()-1
                    ? URLDecoder.decode(pair.substring(eq+1), StandardCharsets.UTF_8)
                    : "";
            map.put(k, v);
        }
        return map;
    }



    private static String buildAuthUrl(String redirectUri,
                                       String codeChallenge,
                                       String state) {
        return AUTH_URL + "?" +
                "client_id="        + url(CLIENT_ID) +
                "&response_type=code" +
                "&redirect_uri="    + url(redirectUri) +
                "&scope="           + url(SCOPE) +
                "&code_challenge="  + url(codeChallenge) +
                "&code_challenge_method=S256" +
                "&state="           + url(state) +
                "&kc_idp_hint=google";
    }

    private TokenResponse exchangeForTokens(String code,
                                            String redirectUri,
                                            String codeVerifier) {
        String body = "grant_type=authorization_code" +
                "&client_id="      + url(CLIENT_ID) +
                "&code="           + url(code) +
                "&redirect_uri="   + url(redirectUri) +
                "&code_verifier="  + url(codeVerifier);

        HttpRequest req = HttpRequest.newBuilder(URI.create(TOKEN_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> resp = HttpClient.newHttpClient()
                    .send(req, HttpResponse.BodyHandlers.ofString());

            if (resp.statusCode() != 200)
                throw new RuntimeException("Token endpoint error: " + resp.body());

            return new ObjectMapper().readValue(resp.body(), TokenResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String url(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private boolean trySilentLogin() {
        System.out.println("Trying silent login...");
        return TokenStore.load().map(stored -> {
            long now = System.currentTimeMillis() / 1000;
            if (now < JwtUtils.expires(stored.idToken())) {      // still good
                Platform.runLater(() -> controller.setAuthSuccess(
                        "Welcome " + JwtUtils.preferredUsername(stored.idToken()) + "!"));
                return true;
            }
            /* token expired → try refresh */
            try {
                TokenResponse fresh = RefreshTokenClient
                        .refresh(stored.refreshToken(), TOKEN_URL, CLIENT_ID);
                TokenStore.save(fresh);
                Platform.runLater(() -> controller.setAuthSuccess(
                        "Welcome " + JwtUtils.preferredUsername(fresh.idToken()) + "!"));
                return true;
            } catch (Exception ignored) { return false; }
        }).orElse(false);
    }
}
