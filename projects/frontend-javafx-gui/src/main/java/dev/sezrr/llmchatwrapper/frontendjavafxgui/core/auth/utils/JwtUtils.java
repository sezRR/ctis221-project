package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.auth.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;

public final class JwtUtils {
    private static final ObjectMapper M = new ObjectMapper();

    public static String preferredUsername(String idToken) {
        return get(idToken, "preferred_username");
    }
    
    public static String preferredName(String idToken) {
        return get(idToken, "given_name");
    }
    
    public static String userId(String idToken) {
        return get(idToken, "sub");
    }
    
    public static long expires(String idToken) {
        return Long.parseLong(get(idToken, "exp"));
    }
    
    private static String get(String jwt, String claim) {
        try {
            String payload = jwt.split("\\.")[1];
            byte[] bytes   = Base64.getUrlDecoder().decode(payload);
            JsonNode node  = M.readTree(bytes);
            return node.path(claim).asText();
        } catch (Exception e) { return ""; }
    }
}
