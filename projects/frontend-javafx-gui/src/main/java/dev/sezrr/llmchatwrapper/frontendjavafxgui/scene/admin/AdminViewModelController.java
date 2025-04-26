package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiClient;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.ApiConfig;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.StandardRequestStrategy;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelAdd;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat.UserChatViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class AdminViewModelController {
    private final ApiClient apiClient = new ApiClient(new StandardRequestStrategy(ApiConfig.BASE_API));

    @FXML
    private TableView<ModelQuery> models;

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
    
    private void initColumns() {
        modelIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelApiUrlColumn.setCellValueFactory(new PropertyValueFactory<>("apiUrl"));
    }

    public void init() {
        try {
            initColumns();
            
            CustomResponseEntity<List<ModelQuery>> response = apiClient.get("/models", new TypeReference<>() {});

            models.getItems().clear();
            models.getItems().addAll(response.getData());

            // TODO: Listeners are added for all items in the table, so it will print the selected item multiple times
            models.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    System.out.println("Selected model: " + newValue);
                }
            });
        } catch (Exception ex) {
            showError("Failed to load models", ex.getMessage());
        }
    }

    @FXML
    private void backToUserChat(MouseEvent event) {
        UserChatViewController userChatViewController = SceneManager.switchScene(SceneConstant.USER_CHAT_VIEW);
        userChatViewController.setCurrentViewValue(UserChatViewController.View.USER_VIEW);
    }

    @FXML
    private void addModel(ActionEvent event) {
        if (!areInputsValid()) {
            showError("Invalid Input", "Please fill in all fields.");
            return;
        }

        try {
            String model = modelName.getText().trim();
            String apiUrlValue = apiUrl.getText().trim();

            ModelAdd modelAdd = new ModelAdd(model, apiUrlValue);
            CustomResponseEntity<ModelQuery> response = apiClient.post("/models", modelAdd, new TypeReference<>() {});

            System.out.println("Model added: " + response.getData());

            // Optionally refresh the list after adding
            init();
        } catch (Exception ex) {
            showError("Failed to add model", ex.getMessage());
        }
    }

    private boolean areInputsValid() {
        return !modelName.getText().trim().isEmpty() && !apiUrl.getText().trim().isEmpty();
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
