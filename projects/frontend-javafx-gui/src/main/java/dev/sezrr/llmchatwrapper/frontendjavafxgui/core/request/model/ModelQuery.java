package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model;

public class ModelQuery {
    private String id;
    private String model;
    private String apiUrl;
    private Object activeModelPricing; // TODO: Replace with actual type

    public ModelQuery() {
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

    public Object getActiveModelPricing() {
        return activeModelPricing;
    }

    public void setActiveModelPricing(Object activeModelPricing) {
        this.activeModelPricing = activeModelPricing;
    }

    @Override
    public String toString() {
        return "ModelQuery{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", activeModelPricing=" + activeModelPricing +
                '}';
    }
}
