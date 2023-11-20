package project5;

/*
 * VIEW - this class is the graphical interface the user interacts with
 * uses data from model in memory to create the graphical interface
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Labels
        Label instrumentTypeLabel = new Label("Instrument Type: ");
        Label instrumentBrandLabel = new Label("Instrument Brand: ");
        Label maxCostLabel = new Label("Maximum Cost: ");
        Label warehouseLabel = new Label("Warehouse Location: ");

        // ComboBoxes
        ComboBox<String> instrumentTypeComboBox = new ComboBox<>();
        instrumentTypeComboBox.getItems().addAll("Option1", "Option2", "Option3");

        ComboBox<String> instrumentBrandComboBox = new ComboBox<>();
        instrumentBrandComboBox.getItems().addAll("Option1", "Option2", "Option3");

        ComboBox<String> warehouseComboBox = new ComboBox<>();
        warehouseComboBox.getItems().addAll("Warehouse1", "Warehouse2", "All");

        // Text field
        TextField maxCostTextField = new TextField();

        // Button
        Button submitButton = new Button("Submit Request");

        // VBox layout
        VBox root = new VBox(10);
        root.getChildren().addAll(
                instrumentTypeLabel, instrumentTypeComboBox,
                instrumentBrandLabel, instrumentBrandComboBox,
                maxCostLabel, maxCostTextField,
                warehouseLabel, warehouseComboBox,
                submitButton
        );

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Instrument Request");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

