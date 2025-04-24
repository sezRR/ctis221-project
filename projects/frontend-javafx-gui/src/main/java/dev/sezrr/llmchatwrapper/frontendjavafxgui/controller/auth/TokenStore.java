package dev.sezrr.llmchatwrapper.frontendjavafxgui.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Optional;

public final class TokenStore {

    private static final Path DIR  = Path.of(System.getProperty("user.home"), ".javafx-oauth");
    private static final Path FILE = DIR.resolve("tokens.json");
    private static final ObjectMapper M = new ObjectMapper().registerModule(new JavaTimeModule());

    /* ---------- public API ------------------------------------------------ */

    public static Optional<SavedTokens> load() {
        try {
            if (!Files.exists(FILE)) return Optional.empty();
            return Optional.of(M.readValue(Files.readString(FILE), SavedTokens.class));
        } catch (Exception e) {
            /* Corrupted →  rename file and force fresh login next run */
            try { Files.move(FILE, FILE.resolveSibling("tokens.bad"),
                    StandardCopyOption.REPLACE_EXISTING); }
            catch (Exception ignored) {}
            return Optional.empty();
        }
    }

    public static void save(TokenResponse r) {
        try {
            Files.createDirectories(DIR);
            SavedTokens s = new SavedTokens(
                    r.idToken(),
                    r.refreshToken(),
                    Instant.now().plusSeconds(r.expiresIn()));

            /* --- atomic write: temp‑file then move ---------------------- */
            Path tmp = Files.createTempFile(DIR, "tok", ".tmp");
            M.writerWithDefaultPrettyPrinter().writeValue(tmp.toFile(), s);
            Files.move(tmp, FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void delete() {
        try { Files.deleteIfExists(FILE); } catch (Exception ignored) {}
    }

    /* ---------- DTO ------------------------------------------------------ */
    public record SavedTokens(String idToken, String refreshToken, Instant expiry) {}
}
