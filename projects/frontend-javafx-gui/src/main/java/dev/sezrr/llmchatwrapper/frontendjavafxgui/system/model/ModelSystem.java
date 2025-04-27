package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.model;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiConfig;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.StandardRequestStrategy;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ModelSystem {
    private static List<ModelQuery> models = new ArrayList<>();
    private static final ApiClient apiClient = new ApiClient(new StandardRequestStrategy(ApiConfig.BASE_API));

    public static CustomResponseEntity<?> addModel(ModelRequest modelRequest) throws Exception {
        if (hasModel(modelRequest.model(), modelRequest.apiUrl()))
            return CustomResponseEntity.failure("You cannot add the same model twice");

        CustomResponseEntity<ModelQuery> response = apiClient.post("/models", modelRequest, new TypeReference<>() {});
        models.add(response.getData());
        
        return CustomResponseEntity.success("Model added successfully");
    }
    
    public static boolean hasModel(String model, String apiUrl) {
        for (ModelQuery modelQ: models) {
            if (modelQ.getModel().equals(model) && modelQ.getApiUrl().equals(apiUrl)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasModel(String id) {
        for (ModelQuery modelQ: models) {
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
        
        CustomResponseEntity<Boolean> response = apiClient.delete("/models/" + id, new TypeReference<>() {});
         
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
        CustomResponseEntity<List<ModelQuery>> response = apiClient.get("/models", new TypeReference<>() {});
        if (response == null)
            return null;

        models.clear();
        
        if (!response.getData().isEmpty()) {
            models = response.getData();
        } 
        
        return models;
    }
}
