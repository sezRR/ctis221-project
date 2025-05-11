package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.model;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiConfig;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.StandardRestRequestStrategy;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelPricingRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ModelSystem {
    private static List<ModelQuery> models = new ArrayList<>();
    private static final ApiClient apiClient = new ApiClient(new StandardRestRequestStrategy(ApiConfig.BASE_API));

    public static CustomResponseEntity<?> addModel(ModelRequest modelRequest) throws Exception {
        if (hasModel(modelRequest.model(), modelRequest.apiUrl()))
            return CustomResponseEntity.failure("You cannot add the same model twice");

        CustomResponseEntity<ModelQuery> response = apiClient.post("/models", modelRequest, new TypeReference<>() {
        });
        models.add(response.getData());

        return CustomResponseEntity.success("Model added successfully");
    }
    
    public static CustomResponseEntity<?> addPricingToModel(ModelPricingRequest modelPricingRequest) throws Exception {
//        if (!hasModel(modelPricingRequest.modelId()))
//            return CustomResponseEntity.failure("You cannot add pricing to a model that does not exist");

//        CustomResponseEntity<ModelQuery> response = apiClient.post("/models/" + modelPricingRequest.modelId() + "/pricing", modelPricingRequest, new TypeReference<>() {
//        });

        CustomResponseEntity<ModelQuery> response = apiClient.post("/model-pricings", modelPricingRequest, new TypeReference<>() {
        });
        models.add(response.getData());

        return CustomResponseEntity.success("Model pricing added successfully");
    }

    public static ModelQuery getModel(String model) throws Exception {
        for (ModelQuery modelQ : models) {
            if (modelQ.getModel().equals(model)) {
                return modelQ;
            }
        }
        return null;
    }

    public static boolean hasModel(String model, String apiUrl) {
        for (ModelQuery modelQ : models) {
            if (modelQ.getModel().equals(model) && modelQ.getApiUrl().equals(apiUrl)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasModel(String id) {
        for (ModelQuery modelQ : models) {
            if (modelQ.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean deleteModel(String id) {
        var isExists = hasModel(id);
        if (!isExists)
            return false;

        CustomResponseEntity<Boolean> response = apiClient.delete("/models/" + id, new TypeReference<>() {
        });

        if (response == null || !response.isSuccess())
            return false;

        models.removeIf(model -> model.getId().equals(id));
        return true;
    }

    public static String displayModels() {
        String output = "";
        for (ModelQuery model : models) {
            output += model.getModel() + "\t" + model.getApiUrl() + "\n";
        }
        return output;
    }

    public static List<ModelQuery> getAllModels() {
        CustomResponseEntity<List<ModelQuery>> response = apiClient.get("/models", new TypeReference<>() {
        });
        if (response == null)
            return null;

        models.clear();

        if (!response.getData().isEmpty()) {
            models = response.getData();
        }

        return models;
    }

    public static List<ModelQuery> getModelsByApiUrlAndModel(String model, String apiUrl) {
        List<ModelQuery> filteredModels = new ArrayList<>();
        for (ModelQuery modelQ : models) {
            if (modelQ.getApiUrl().equals(apiUrl) || modelQ.getModel().equals(model)) {
                filteredModels.add(modelQ);
            }
        }
        return filteredModels;
    }

    public static List<ModelQuery> getModels() {
        return models;
    }

    public static double getMinPrice() {
        double minPrice = Double.MAX_VALUE;
        for (ModelQuery model : models) {
            if (model.getActiveModelPricing() == null)
                continue;
            
            if (model.getActiveModelPricing().getAdditionalPrice() < minPrice) {
                minPrice = model.getActiveModelPricing().getAdditionalPrice();
            }
        }
        return minPrice;
    }

    public static double getMaxPrice() {
        double maxPrice = Double.MIN_VALUE;
        for (ModelQuery model : models) {
            if (model.getActiveModelPricing() == null)
                continue;
            
            if (model.getActiveModelPricing().getAdditionalPrice() > maxPrice) {
                maxPrice = model.getActiveModelPricing().getAdditionalPrice();
            }
        }
        return maxPrice;
    }
    
    public static double getAvgPrice() {
        double totalPrice = 0;
        for (ModelQuery model : models) {
            if (model.getActiveModelPricing() == null)
                continue;
            
            totalPrice += model.getActiveModelPricing().getAdditionalPrice();
        }
        return totalPrice / getTotalPlans();
    }
    
    public static int getTotalPlans() {
        // Check if model has active pricing
        int totalPlans = 0;
        for (ModelQuery model : models) {
            if (model.getActiveModelPricing() != null) {
                totalPlans++;
            }
        }
        return totalPlans;
    }
}
