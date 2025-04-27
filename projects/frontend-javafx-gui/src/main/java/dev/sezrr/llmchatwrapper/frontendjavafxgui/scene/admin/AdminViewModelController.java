package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.alert.AlertUtil;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat.UserChatViewController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.system.model.ModelSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminViewModelController {
    @FXML
    private TableView<ModelQuery> models;
    
    @FXML
    private Button buttonDeleteModel;

    @FXML
    private Button buttonDetails;

    @FXML
    private Button buttonUpdate;

    @FXML
    private TableColumn<ModelQuery, String> modelIdColumn;
    
    @FXML
    private TableColumn<ModelQuery, String> modelNameColumn;
    
    @FXML
    private TableColumn<ModelQuery, String> modelApiUrlColumn;

    @FXML
    private TextField modelName;

    @FXML
    private TextField apiUrl;
    
    @FXML
    private Label selectedIndicator;
    
    @FXML
    private Button buttonResetSearch;
    
    private void initColumns() {
        modelIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelApiUrlColumn.setCellValueFactory(new PropertyValueFactory<>("apiUrl"));
    }
    
    public void init() {
        try {
            clearFields();
            initColumns();

            var allModels  = ModelSystem.getAllModels();
            
            if (allModels == null || allModels.isEmpty()) {
                models.getItems().clear();
                AlertUtil.showInfo("No Models Found", "No models found in the system.");
                return;
            }

            models.getItems().clear();
            models.getItems().addAll(allModels);

            // TODO: Listeners are added for all items in the table, so it will print the selected item multiple times
            models.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    buttonDeleteModel.setDisable(false);
                    buttonUpdate.setDisable(false);
                    buttonDetails.setDisable(false);
                    System.out.println("Selected model: " + newValue);

                    selectedIndicator.setText("Selected Model: " + newValue.getId());
                } else {
                    buttonDeleteModel.setDisable(true);
                    buttonUpdate.setDisable(true);
                    buttonDetails.setDisable(true);
                    selectedIndicator.setText("Selected model: None");
                }
            });
        } catch (Exception ex) {
            AlertUtil.showError("Failed to load models", ex.getMessage());
        }
    }
    
    private void clearFields() {
        modelName.clear();
        apiUrl.clear();
        
        // Clear focus
        modelName.requestFocus();
        modelName.getParent().requestFocus();
        apiUrl.requestFocus();
        apiUrl.getParent().requestFocus();
        models.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void backToUserChat(MouseEvent event) {
        clearFields();
        
        UserChatViewController userChatViewController = SceneManager.switchScene(SceneConstant.USER_CHAT_VIEW);
        userChatViewController.setCurrentViewValue(UserChatViewController.View.USER_VIEW);
    }
    
    @FXML
    private void searchModel(ActionEvent e) {
        String modelNameText = modelName.getText().trim();
        String apiUrlText = apiUrl.getText().trim();
        
        var result = ModelSystem.getModelsByApiUrlAndModel(modelNameText, apiUrlText);
        if (result.isEmpty()) {
            AlertUtil.showInfo("No Models Found", "No models found with the given criteria.");
            return;
        }

        models.getItems().clear();
        models.getItems().addAll(result);
        buttonResetSearch.setDisable(false);
    }
    
    @FXML
    private void onResetSearchButtonClicked(ActionEvent event) {
        clearFields();
        buttonResetSearch.setDisable(true);

        models.getItems().clear();
        var allModelsCache = ModelSystem.getModels();
        models.getItems().addAll(allModelsCache);
    }

    @FXML
    private void addModel(ActionEvent event) {
        if (!areInputsValid()) {
            AlertUtil.showError("Invalid Input", "Please fill in all fields.");
            return;
        }

        try {
            String model = modelName.getText().trim();
            String apiUrlValue = apiUrl.getText().trim().toLowerCase();

            var result = ModelSystem.addModel(new ModelRequest(model, apiUrlValue));
            if (!result.isSuccess()) {
                AlertUtil.showError("Failed to add model", result.getMessage());
                return;
            }
            
            AlertUtil.showSuccess("Model added", "Model added successfully");

            // Refresh the list after adding
            init();
        } catch (Exception ex) {
            AlertUtil.showError("Failed to add model", ex.getMessage());
        }
    }
    
    @FXML
    private void deleteModel(ActionEvent event) {
        ModelQuery selectedModel = models.getSelectionModel().getSelectedItem();
        if (selectedModel == null) {
            AlertUtil.showError("No model selected", "Please select a model to delete.");
            return;
        }
        
        buttonResetSearch.setDisable(true);

        try {
            String modelId = selectedModel.getId();
            boolean result = ModelSystem.deleteModel(modelId);
            if (!result) {
                AlertUtil.showError("Failed to delete model", "Model not found.");
                return;
            }
            
            AlertUtil.showSuccess("Model deleted", "Model deleted successfully");

            // Refresh the list after deletion
            init();
        } catch (Exception ex) {
            AlertUtil.showError("Failed to delete model", ex.getMessage());
        }
    }
    
    @FXML
    private void onClickButtonDetails(ActionEvent e) {
        AlertUtil.showInfo("Implementation", "This feature is not implemented yet.");
    }
    
    @FXML
    private void onClickButtonUpdate(ActionEvent e) {
        AlertUtil.showInfo("Implementation", "This feature is not implemented yet.");
    }

    private boolean areInputsValid() {
        return !modelName.getText().trim().isEmpty() && !apiUrl.getText().trim().isEmpty();
    }
}
