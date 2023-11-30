package project5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application{

    private Label instrumentTypeLabel;
    private Label instrumentBrandLabel;
    private Label maxCostLabel;
    private Label warehouseLabel;
    
    private Scanner in;
	private PrintWriter out;

    private ComboBox<String> instrumentTypeComboBox;
    private ComboBox<String> instrumentBrandComboBox;
    private ComboBox<String> warehouseComboBox;

    private TextField maxCostTextField;

    private Button submitButton;

    private VBox root;
    
    private Controller controller;

    public View() {
    	//initializeComponents();
    }
    
    public VBox getRoot() {
        return root;
    }
    
    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Query Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    public String getInstrumentTypeComboBox() {
//        return instrumentTypeComboBox.getValue();
//    }
//    
//    public String getInstrumentBrandComboBox() {
//        return instrumentBrandComboBox.getValue();
//    }
//
//    public String getWarehouseComboBox() {
//        return warehouseComboBox.getValue();
//    }
//
//    public String getMaxCostTextField() {
//        return maxCostTextField.getText();
//    }
//    
//    public Button getSubmitButton() {
//    	return submitButton;
//    }
    
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
        submitButton.setOnAction(e -> handleSubmitButtonAction());
        //submitButton.setOnAction(); 

        root = new VBox(10);
        root.getChildren().addAll(
                instrumentTypeLabel, instrumentTypeComboBox,
                instrumentBrandLabel, instrumentBrandComboBox,
                maxCostLabel, maxCostTextField,
                warehouseLabel, warehouseComboBox,
                submitButton
        );
    }
    
    private void handleSubmitButtonAction() {
    	String instrumentType = instrumentTypeComboBox.getValue();
        String instrumentBrand = instrumentBrandComboBox.getValue();
        String maxCost = maxCostTextField.getText();
        String warehouse = warehouseComboBox.getValue();

        // Send data to the server
        out.println(instrumentType + "@" + instrumentBrand + "@" + maxCost + "@" + warehouse);
        out.flush();
        
        while(in.nextLine() != null) {
        	System.out.println("Hi");
        }
    }



    public static void main(String[] args) throws IOException{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final int SBAP_PORT = 8888;
	    Socket s = new Socket("localhost", SBAP_PORT);
	    InputStream instream = s.getInputStream();
	    OutputStream outstream = s.getOutputStream();
	    in = new Scanner(instream);
	    out = new PrintWriter(outstream); 
        primaryStage.setTitle("Instrument Request");
        
        initializeComponents();
        
		Scene scene = new Scene(getRoot(), 500, 350); // Create the scene with the root from view
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
