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
    
    private void initializeComponents() {
        instrumentTypeLabel = new Label("Instrument Type: ");
        instrumentBrandLabel = new Label("Instrument Brand: ");
        maxCostLabel = new Label("Maximum Cost: ");
        warehouseLabel = new Label("Warehouse Location: ");

        instrumentTypeComboBox = new ComboBox<>();
        instrumentTypeComboBox.getItems().addAll("all", "guitar", "bass", "drums", "keyboard");
        

        instrumentBrandComboBox = new ComboBox<>();
        //instrumentBrandComboBox.getItems().addAll("all", "ludwig", "gibson", "fender");
        instrumentTypeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            instrumentBrandComboBox.getItems().clear(); // Clear previous items

            if (newValue != null) {
                switch (newValue) {
                    case "guitar":
                        instrumentBrandComboBox.getItems().addAll("gibson", "yamaha");
                        break;
                    case "bass":
                        instrumentBrandComboBox.getItems().addAll("fender");
                        break;
                    case "drums":
                        instrumentBrandComboBox.getItems().addAll("alesis", "roland");
                        break;
                    case "keyboard":
                        instrumentBrandComboBox.getItems().addAll("yamaha", "ludwig");
                        break;
                    case "all":
                        instrumentBrandComboBox.getItems().addAll("ludwig", "gibson", "fender", "yamaha", "alesis", "roland");
                        break;
                }
            }
        });
        
//        instrumentTypeComboBox.getSelectionModel().selectedItemProperty().addListener(observable -> {
//            instrumentBrandComboBox.getItems().clear(); // Clear previous items
//
//            String selectedType = instrumentTypeComboBox.getValue();
//
//            if ("guitar".equals(selectedType)) {
//                instrumentBrandComboBox.getItems().addAll("gibson", "yamaha");
//            } else if ("bass".equals(selectedType)) {
//                instrumentBrandComboBox.getItems().addAll("fender");
//            } else if ("drums".equals(selectedType)) {
//                instrumentBrandComboBox.getItems().addAll("alesis", "Roland");
//            } else if ("keyboard".equals(selectedType)) {
//                instrumentBrandComboBox.getItems().addAll("yamaha", "ludwig");
//            } else if ("all".equals(selectedType)) {
//                instrumentBrandComboBox.getItems().addAll("ludwig", "gibson", "fender", "yamaha", "alesis", "roland");
//            }
//        });


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
        
        if(instrumentType == null || instrumentBrand == null || maxCost == null || warehouse == null) {
        	showMessage("Invalid Query");
        }
        else {


        // Send data to the server
        out.println(instrumentType + "@" + instrumentBrand + "@" + maxCost + "@" + warehouse);
        out.flush();

        // Read the response from the server until the special marker
        StringBuilder serverResponse = new StringBuilder();
        String line;
        while (!(line = in.nextLine()).equals("END_OF_RESPONSE")) {
            serverResponse.append(line).append("\n");
        }
        showMessage(serverResponse.toString());
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
