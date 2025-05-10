package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelQuery {
    private String id;
    private String model;
    private String apiUrl;
    private ModelPricingQuery activeModelPricing;

    public ModelQuery() {
    }
    
    public ModelQuery(String model, String apiUrl) {
        this.model = model;
        this.apiUrl = apiUrl;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public ModelPricingQuery getActiveModelPricing() {
        return activeModelPricing;
    }

    public void setActiveModelPricing(ModelPricingQuery activeModelPricing) {
        this.activeModelPricing = activeModelPricing;
    }

    @Override
    public String toString() {
        return id + "\t" + model + "\t" + apiUrl;
    }
}
