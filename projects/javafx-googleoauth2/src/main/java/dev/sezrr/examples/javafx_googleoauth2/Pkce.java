package dev.sezrr.examples.javafx_googleoauth2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

final class Pkce {
    private static final SecureRandom RNG = new SecureRandom();

    static String createVerifier() {
        byte[] bytes = new byte[64];
        RNG.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    static String challengeS256(String verifier) {
        try {
            byte[] sha = MessageDigest.getInstance("SHA-256")
                    .digest(verifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(sha);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
