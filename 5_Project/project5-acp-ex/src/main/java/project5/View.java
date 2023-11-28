package project5;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View{
    /*
     * Instance variable declarations
     */
    private Label instrumentTypeLabel;
    private Label instrumentBrandLabel;
    private Label maxCostLabel;
    private Label warehouseLabel;

    private ComboBox<String> instrumentTypeComboBox;
    private ComboBox<String> instrumentBrandComboBox;
    private ComboBox<String> warehouseComboBox;

    private TextField maxCostTextField;

    private Button submitButton;

    private VBox root;

    /*
     * Constructor
     */
    public View() {
    	initializeComponents();
    }

    /*
     * Initialize UI components
     */
    private void initializeComponents() {
        instrumentTypeLabel = new Label("Instrument Type: ");
        instrumentBrandLabel = new Label("Instrument Brand: ");
        maxCostLabel = new Label("Maximum Cost: ");
        warehouseLabel = new Label("Warehouse Location: ");

        instrumentTypeComboBox = new ComboBox<>();
        instrumentTypeComboBox.getItems().addAll("all", "guitar", "bass", "drums", "keyboard");

        instrumentBrandComboBox = new ComboBox<>();
        instrumentBrandComboBox.getItems().addAll("all", "ludwig", "gibson", "fender");

        warehouseComboBox = new ComboBox<>();
        warehouseComboBox.getItems().addAll("all", "Pensacola, Florida", "Charlotte, North Carolina", "Dallas, Fort Worth Texas");

        maxCostTextField = new TextField();

        submitButton = new Button("Submit Request");

        root = new VBox(10);
        root.getChildren().addAll(
                instrumentTypeLabel, instrumentTypeComboBox,
                instrumentBrandLabel, instrumentBrandComboBox,
                maxCostLabel, maxCostTextField,
                warehouseLabel, warehouseComboBox,
                submitButton
        );
    }

    /*
     * Refresh the GUI with new or changed information when the user does stuff
     */
    private void updateView() {
        // Update view components
    }
    
    public VBox getRoot() {
        return root;
    }

    /*
     * Getters for UI components (if needed)
     */

    public String getInstrumentTypeComboBox() {
        return instrumentTypeComboBox.getValue();
    }

    public String getInstrumentBrandComboBox() {
        return instrumentBrandComboBox.getValue();
    }

    public String getWarehouseComboBox() {
        return warehouseComboBox.getValue();
    }

    public String getMaxCostTextField() {
        return maxCostTextField.getText();
    }
    
    public Button getSubmitButton() {
    	return submitButton;
    }
}
