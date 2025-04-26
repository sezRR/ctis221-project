package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat.UserChatViewController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.JwtUtils;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils.Pkce;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.RefreshTokenClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenResponse;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.token.TokenStore;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
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
    private final HostServices hostServices;
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private static void onSuccessSwitchToChatView(String username) {
        // Handle failure callback
        System.out.println("Login successful for user: " + username);
        
        // Switch back to the login view
        UserChatViewController userChatViewController = SceneManager.switchScene(SceneConstant.USER_CHAT_VIEW);
        userChatViewController.init(username);
    }
    
    public static ObjectMapper getMapper() {
        return mapper;
    }

    public AuthService(HostServices hostServices) {
        this.hostServices = hostServices;
        var isSuccess = trySilentLogin();
        if (!isSuccess)
            // Load the login view
            SceneManager.switchScene(SceneConstant.LOGIN_VIEW);
    }
    
    public void startAuthFlow() {
        if (!trySilentLogin()) {
            try {
                beginOAuthFlow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean trySilentLogin() {
        return TokenStore.load().map(stored -> {
            long now = System.currentTimeMillis() / 1000;
            if (now < JwtUtils.expires(stored.idToken())) {
                onSuccessSwitchToChatView(JwtUtils.preferredName(stored.idToken()));
                return true;
            }
            try {
                TokenResponse fresh = RefreshTokenClient.refresh(stored.refreshToken(), AuthConfig.TOKEN_URL, AuthConfig.CLIENT_ID);
                TokenStore.save(fresh);
                onSuccessSwitchToChatView(JwtUtils.preferredName(fresh.idToken()));
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }).orElse(false);
    }
    
    private String constructAuthUrl(String redirectUri, String codeChallenge, String state) {
        return AuthConfig.AUTH_URL +
                "?client_id=" + url(AuthConfig.CLIENT_ID) +
                "&response_type=code" +
                "&redirect_uri=" + url(redirectUri) +
                "&scope=" + url(AuthConfig.SCOPE) +
                "&code_challenge=" + url(codeChallenge) +
                "&code_challenge_method=S256" +
                "&state=" + url(state) +
                "&kc_idp_hint=google";
    }
    
    private void beginOAuthFlow() throws IOException {
        int port = findFreePort();
        String redirectUri = "http://127.0.0.1:" + port + "/cb";

        String codeVerifier  = Pkce.createVerifier();
        String codeChallenge = Pkce.challengeS256(codeVerifier);
        String state         = UUID.randomUUID().toString();

        String authUrl = constructAuthUrl(redirectUri, codeChallenge, state);
        hostServices.showDocument(authUrl);

        createHttpServerToGetToken(port, state, redirectUri, codeVerifier);
    }

    private void createHttpServerToGetToken(int port, String state, String redirectUri, String codeVerifier) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/cb", exchange -> {
            Map<String, String> params = parseQuery(exchange.getRequestURI().getRawQuery());
            if (!state.equals(params.get("state"))) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }
            TokenResponse tok = exchangeForTokens(params.get("code"), redirectUri, codeVerifier);
            TokenStore.save(tok);

            setHTTPOnlyCookie(exchange, tok);

            showSuccessHTML(exchange);
            server.stop(0);

            String username = JwtUtils.preferredName(tok.idToken());
            Platform.runLater(() -> onSuccessSwitchToChatView(username)); // Ensure scene switch happens on JavaFX Application Thread
        });
        server.start();
    }

    private static void setHTTPOnlyCookie(HttpExchange exchange, TokenResponse tok) {
        String cookie = "id_token=" + URLEncoder.encode(tok.idToken(), StandardCharsets.UTF_8) +
                "; Max-Age=" + tok.expiresIn() +
                "; Path=/; HttpOnly; SameSite=Lax";
        exchange.getResponseHeaders().add("Set-Cookie", cookie);
    }

    private static void showSuccessHTML(HttpExchange exchange) throws IOException {
        byte[] body = "<html><body><h2>You may return to the app.</h2></body></html>"
                .getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, body.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body);
        }
    }

    private static int findFreePort() throws IOException {
        try (ServerSocket s = new ServerSocket(0)) {
            return s.getLocalPort();
        }
    }

    private static Map<String,String> parseQuery(String raw) throws IOException {
        Map<String,String> map = new HashMap<>();
        if (raw == null || raw.isEmpty()) return map;
        for (String pair : raw.split("&")) { // Custom URL Query Param Parsing for format ?key=value&key2=value2
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
                "&client_id=" + url(AuthConfig.CLIENT_ID) +
                "&code=" + url(code) +
                "&redirect_uri=" + url(redirectUri) +
                "&code_verifier=" + url(codeVerifier);

        HttpRequest req = HttpRequest.newBuilder(URI.create(AuthConfig.TOKEN_URL))
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
