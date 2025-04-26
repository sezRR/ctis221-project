package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.kerem.Model;

import java.util.ArrayList;

public class ModelSystem {

    protected ArrayList<Model> models = new ArrayList<>();

    public void addModel(String modelName, String apiURL) throws Exception {
        Model m = null;
        for (Model model : models) {

            if (model.getModelName().equals(modelName) && model.getApiURL().equals(apiURL)) {
                throw new Exception("Already have this model");
            }
            else {
                m = new Model(modelName, apiURL);
                models.add(model);
            }
        }
    }

    public boolean deleteModel(String modelName, String apiURL) {
        for (Model model : models) {
            if (model.getModelName().equals(modelName) && model.getApiURL().equals(apiURL)) {
                models.remove(model);
                return true;
            }

        }
        return false;
    }

    public String displayModels() {

        String output = "";
        for (Model model : models) {
            output += model.getModelName() + "\t" + model.getApiURL() + "\n";
        }
        return output;
    }
}
