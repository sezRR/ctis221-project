package Model;

public class Model {

   private String modelName;
   private String apiURL;

    public Model(String apiURL, String modelName) {
        this.apiURL = apiURL;
        this.modelName = modelName;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}

