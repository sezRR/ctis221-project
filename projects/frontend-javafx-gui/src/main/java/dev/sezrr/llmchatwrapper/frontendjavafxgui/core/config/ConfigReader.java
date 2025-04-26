package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigReader {
    private static final Map<String, Object> config;

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.yaml")) {
            if (input == null) {
                throw new RuntimeException("config.yaml not found in resources");
            }
            Yaml yaml = new Yaml();
            config = yaml.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static String get(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = config;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.get(keys[i]);
            if (current == null) {
                throw new RuntimeException("Invalid configuration key: " + key);
            }
        }
        Object value = current.get(keys[keys.length - 1]);
        if (value == null) {
            throw new RuntimeException("Invalid configuration key: " + key);
        }
        return value.toString();
    }
}
