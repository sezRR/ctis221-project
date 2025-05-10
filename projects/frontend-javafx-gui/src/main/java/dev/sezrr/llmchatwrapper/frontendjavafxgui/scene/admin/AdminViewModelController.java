package dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.admin;

import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.alert.AlertUtil;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelPricingQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelPricingRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelRequest;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model.ModelQuery;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.response_entity.CustomResponseEntity;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.core.scene.SceneManager;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.SceneConstant;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.scene.chat.UserChatViewController;
import dev.sezrr.llmchatwrapper.frontendjavafxgui.system.model.ModelSystem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.RangeSlider;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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

    @FXML
    private Label minPrice;

    @FXML
    private Label maxPrice;

    @FXML
    private Label avgPrice;

    @FXML
    private Label totalPlans;

    @FXML
    private TableView<ModelQuery> pricingModels;

    @FXML
    private TableColumn<ModelQuery, String> pricingIdColumn;

    @FXML
    private TableColumn<ModelQuery, String> pricingModelNameColumn;

    @FXML
    private TableColumn<ModelQuery, String> pricingAdditionalPriceColumn;

    @FXML
    private TableColumn<ModelQuery, String> pricingDetailsColumn;

    @FXML
    private TableColumn<ModelQuery, String> pricingActiveColumn;

    @FXML
    private ComboBox<ModelQuery> pricingModelsComboBox;

    @FXML
    private TextField pricingTabAdditionalPrice;

    @FXML
    private TextField pricingTabDetails;

    @FXML
    private CheckBox pricingTabActive;

    private ModelQuery selectedModelQueryPricingTab;

    @FXML
    private Button tabPricingButtonHistory;

    @FXML
    private Button tabPricingButtonDisable;

    @FXML
    private Button tabPricingButtonUpdate;

    @FXML
    private Button tabPricingButtonDelete;

    @FXML
    private RangeSlider tabPricingsRangeFilter;

    @FXML
    private Label tabPricingsRangeFilterMinLabel;

    @FXML
    private Label tabPricingsRangeFilterMaxLabel;

    private FilteredList<ModelQuery> filteredPricingModels;

    private final DecimalFormat df = new DecimalFormat("#0.00", new DecimalFormatSymbols() {{
        setDecimalSeparator('.');
    }});

    private void initPricingStats() {
        double min = ModelSystem.getMinPrice(); // e.g., 0.05
        double max = ModelSystem.getMaxPrice(); // e.g., 1.00

        minPrice.setText("$" + df.format(min));
        maxPrice.setText("$" + df.format(max));
        avgPrice.setText("$" + df.format(ModelSystem.getAvgPrice()));
        totalPlans.setText(String.valueOf(ModelSystem.getTotalPlans()));

        // Set range slider bounds and initial selection
        tabPricingsRangeFilter.setMin(min);
        tabPricingsRangeFilter.setMax(max);
        tabPricingsRangeFilter.setLowValue(min);
        tabPricingsRangeFilter.setHighValue(max);

        // Set initial label texts with parentheses
        tabPricingsRangeFilterMinLabel.setText("$" + df.format(min) + " ($" + df.format(min) + ")");
        tabPricingsRangeFilterMaxLabel.setText("$" + df.format(max) + " ($" + df.format(max) + ")");

        // Add listeners to update parentheses on slider movement
        tabPricingsRangeFilter.lowValueProperty().addListener((obs, oldVal, newVal) -> {
            tabPricingsRangeFilterMinLabel.setText(
                    "$" + df.format(min) + " ($" + df.format(newVal.doubleValue()) + ")"
            );

            updateFilteredPricingTable();
        });

        tabPricingsRangeFilter.highValueProperty().addListener((obs, oldVal, newVal) -> {
            tabPricingsRangeFilterMaxLabel.setText(
                    "$" + df.format(max) + " ($" + df.format(newVal.doubleValue()) + ")"
            );

            updateFilteredPricingTable();
        });
    }

    private void updateFilteredPricingTable() {
        double low = Double.parseDouble(df.format(tabPricingsRangeFilter.getLowValue()));
        double high = Double.parseDouble(df.format(tabPricingsRangeFilter.getHighValue()));
        System.out.println(low + "  " + high);

        filteredPricingModels.setPredicate(model -> {
            var pricing = model.getActiveModelPricing();
            if (pricing == null || pricing.getAdditionalPrice() == null) return false;
            double price = pricing.getAdditionalPrice();
            return price >= low && price <= high;
        });
    }


    private void initPricingTabModelsComboBox() {
        pricingModelsComboBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(ModelQuery item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getModel());
                }
            }
        });

        pricingModelsComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(ModelQuery item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getModel());
                }
            }
        });

        pricingModelsComboBox.getItems().clear();
//        pricingModelsComboBox.getItems().addAll(ModelSystem.getModels().stream().map(ModelQuery::getModel).toList());
        pricingModelsComboBox.getItems().addAll(ModelSystem.getModels());

        pricingModelsComboBox.setOnAction(event -> {
            selectedModelQueryPricingTab = pricingModelsComboBox.getSelectionModel().getSelectedItem();
            if (selectedModelQueryPricingTab != null) {
                if (selectedModelQueryPricingTab.getActiveModelPricing() == null) {
                    pricingTabAdditionalPrice.setText("0.0");
                    pricingTabDetails.setText("");
                    pricingTabActive.setSelected(false);
                } else {
                    pricingTabAdditionalPrice.setText(String.valueOf(selectedModelQueryPricingTab.getActiveModelPricing().getAdditionalPrice()));
                    pricingTabDetails.setText(selectedModelQueryPricingTab.getActiveModelPricing().getDetails());
                    pricingTabActive.setSelected(selectedModelQueryPricingTab.getActiveModelPricing().getActive());
                }
            }
        });
    }

    private void initPricingsTabColumns() {
        pricingIdColumn.setCellValueFactory(cellData -> {
            ModelQuery model = cellData.getValue();
            ModelPricingQuery pricing = model.getActiveModelPricing();
            return new SimpleStringProperty(pricing != null && pricing.getId() != null ? String.valueOf(pricing.getId()) : "N/A");
        });

        pricingModelNameColumn.setCellValueFactory(cellData -> {
            ModelQuery model = cellData.getValue();
            return new SimpleStringProperty(model.getModel() != null ? model.getModel() : "N/A");
        });

        pricingAdditionalPriceColumn.setCellValueFactory(cellData -> {
            ModelQuery model = cellData.getValue();
            ModelPricingQuery pricing = model.getActiveModelPricing();
            return new SimpleStringProperty(pricing != null && pricing.getAdditionalPrice() != null
                    ? String.valueOf(pricing.getAdditionalPrice()) : "N/A");
        });

        pricingDetailsColumn.setCellValueFactory(cellData -> {
            ModelQuery model = cellData.getValue();
            ModelPricingQuery pricing = model.getActiveModelPricing();
            return new SimpleStringProperty(pricing != null && pricing.getDetails() != null
                    ? pricing.getDetails() : "N/A");
        });

        pricingActiveColumn.setCellValueFactory(cellData -> {
            ModelQuery model = cellData.getValue();
            ModelPricingQuery pricing = model.getActiveModelPricing();
            return new SimpleStringProperty(pricing != null && pricing.getActive() != null
                    ? String.valueOf(pricing.getActive()) : "N/A");
        });

        // If user clicks on the model in the table, it will select the model in the combo box
        pricingModels.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                pricingModelsComboBox.getSelectionModel().select(newValue);
                tabPricingButtonHistory.setDisable(false);
                tabPricingButtonDisable.setDisable(false);
                tabPricingButtonUpdate.setDisable(false);
                tabPricingButtonDelete.setDisable(false);
            } else {
                pricingModelsComboBox.getSelectionModel().clearSelection();
                tabPricingButtonHistory.setDisable(true);
                tabPricingButtonDisable.setDisable(true);
                tabPricingButtonUpdate.setDisable(true);
                tabPricingButtonDelete.setDisable(true);
            }
        });
    }

    private void initModelsTabColumns() {
        modelIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelApiUrlColumn.setCellValueFactory(new PropertyValueFactory<>("apiUrl"));
    }

    private void initColumns() {
        initModelsTabColumns();
        initPricingsTabColumns();
    }

    private void clearFields() {
        clearModelsTabFields();
        clearPricingTabFields();
    }

    public void init() {
        try {
            clearFields();
            initColumns();

            var allModels = ModelSystem.getAllModels();

            if (allModels == null || allModels.isEmpty()) {
                models.getItems().clear();
                AlertUtil.showInfo("No Models Found", "No models found in the system.");
                return;
            }

            models.getItems().clear();
            models.getItems().addAll(allModels);

            filteredPricingModels = new FilteredList<>(javafx.collections.FXCollections.observableArrayList(allModels));
            pricingModels.setItems(filteredPricingModels);

            initPricingStats();
            initPricingTabModelsComboBox();

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
            ex.printStackTrace();
            System.out.println("Failed to load models: " + ex.getMessage());
            AlertUtil.showError("Failed to load models", ex.getMessage());
        }
    }

    private void clearModelsTabFields() {
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
        clearModelsTabFields();

        UserChatViewController userChatViewController = SceneManager.switchScene(SceneConstant.USER_CHAT_VIEW);
        userChatViewController.setCurrentViewValue(UserChatViewController.View.USER_VIEW);
        userChatViewController.init();
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
        clearModelsTabFields();
        buttonResetSearch.setDisable(true);

        models.getItems().clear();
        var allModelsCache = ModelSystem.getModels();
        models.getItems().addAll(allModelsCache);
    }

    @FXML
    private void addModel(ActionEvent event) {
        if (!areModelTabInputsValid()) {
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

    private boolean areModelTabInputsValid() {
        return !modelName.getText().trim().isEmpty() && !apiUrl.getText().trim().isEmpty();
    }

    @FXML
    private void onClickPricingModelsAdd(ActionEvent e) {
        var isValid = areModelPricingTabInputsValid();
        if (!isValid) {
            AlertUtil.showError("Invalid Input", "Please fill the all necessary fields.");
            return;
        }

        var selectedModel = pricingModelsComboBox.getSelectionModel().getSelectedItem();
        Double additionalPrice = Double.parseDouble(pricingTabAdditionalPrice.getText().trim());
        String details = pricingTabDetails.getText().trim();
        boolean isActive = pricingTabActive.isSelected();

        try {
            var modelPricing = new ModelPricingRequest(selectedModel.getId(), additionalPrice, details, isActive);

            var result = ModelSystem.addPricingToModel(modelPricing);
            if (!result.isSuccess()) {
                AlertUtil.showError("Failed to add model pricing", result.getMessage());
                return;
            }

            AlertUtil.showSuccess("Model pricing added", "Model pricing added successfully");
            init();
        } catch (Exception ex) {
            AlertUtil.showError("Failed to add model pricing", ex.getMessage());
        }
    }

    private boolean areModelPricingTabInputsValid() {
        return !pricingModelsComboBox.getSelectionModel().isEmpty()
                && !pricingTabAdditionalPrice.getText().trim().isEmpty()
                && !pricingTabDetails.getText().trim().isEmpty();
    }

    private void clearPricingTabFields() {
        pricingTabAdditionalPrice.clear();
        pricingTabDetails.clear();
        pricingTabActive.setSelected(false);
        pricingModelsComboBox.getSelectionModel().clearSelection();
    }
}
