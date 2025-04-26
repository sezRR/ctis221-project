package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.arda;
import java.util.ArrayList;
import java.util.UUID;

public class User {
    private final UUID id;
    private String username;
    private String authToken;

    public User(UUID id)
    {
        this.id = id;
    }

    public User(UUID id,String username, String authToken) {
        this.id = id;
        this.username = username;
        this.authToken = authToken;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


}
