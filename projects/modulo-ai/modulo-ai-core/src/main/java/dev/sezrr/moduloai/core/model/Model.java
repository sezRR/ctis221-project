package dev.sezrr.moduloai.core.model;

import java.util.List;

public interface Model {
    String getName();
    
    String getProvider();
    
    List<Capability> getCapabilities();
    Capability getCapability(String capability);
}
