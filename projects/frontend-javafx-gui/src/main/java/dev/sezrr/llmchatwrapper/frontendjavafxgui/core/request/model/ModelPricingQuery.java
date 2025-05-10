package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model;

import java.util.UUID;

public class ModelPricingQuery {
    private UUID id;
    private Double additionalPrice;
    private String details;
    private Boolean active;

    public ModelPricingQuery() {
        
    }

    public ModelPricingQuery(UUID id, Double additionalPrice, String details, Boolean active) {
        this.id = id;
        this.additionalPrice = additionalPrice;
        this.details = details;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(Double additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
