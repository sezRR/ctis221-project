package dev.sezrr.moduloai.core.provider;

import java.util.List;

public interface ModelProvider extends Provider {
    List<String> getModels();
    String getModel(String model);

    boolean supportsTextGeneration(String model);
    boolean supportsEmbeddings(String model);
}
