/***************************************************************
Student Name: Trent Wells, Regan O'Donnell
File Name: View.java
Assignment number: Project 5

***************************************************************/
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

//The view acts as the client side of this project

public class Client extends Application{

    private Label instrumentTypeLabel, instrumentBrandLabel, maxCostLabel, warehouseLabel;
    
    private Scanner in;
	private PrintWriter out;

    private ComboBox<String> instrumentTypeComboBox, instrumentBrandComboBox, warehouseComboBox;

    private TextField maxCostTextField;

    private Button submitButton;

    private VBox root;
    
    private Server controller;

    //Constructor
    public Client() {
    }
    
    //Displays the result set from the DB query
    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Query Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //Creates all of the GUI components in the View along with their action listeners
    private void initializeComponents() {
        instrumentTypeLabel = new Label("Instrument Type: ");
        instrumentBrandLabel = new Label("Instrument Brand: ");
        maxCostLabel = new Label("Maximum Cost: ");
        warehouseLabel = new Label("Warehouse Location: ");

        instrumentTypeComboBox = new ComboBox<>();
        instrumentTypeComboBox.getItems().addAll("all", "guitar", "bass", "drums", "keyboard");

        instrumentBrandComboBox = new ComboBox<>();
        instrumentTypeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            instrumentBrandComboBox.getItems().clear();

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

        warehouseComboBox = new ComboBox<>();
        warehouseComboBox.getItems().addAll("all", "Pensacola, Florida", "Charlotte, North Carolina", "Dallas, Fort Worth Texas");

        maxCostTextField = new TextField();

        submitButton = new Button("Submit Request");
        submitButton.setOnAction(e -> handleSubmitButtonAction());

        root = new VBox(10);
        root.getChildren().addAll(
                instrumentTypeLabel, instrumentTypeComboBox,
                instrumentBrandLabel, instrumentBrandComboBox,
                maxCostLabel, maxCostTextField,
                warehouseLabel, warehouseComboBox,
                submitButton
        );
    }
    
    //This method handles the input information after the submit button is pressed
    private void handleSubmitButtonAction() {
    	String instrumentType = instrumentTypeComboBox.getValue();
        String instrumentBrand = instrumentBrandComboBox.getValue();
        String maxCost = maxCostTextField.getText();
        String warehouse = warehouseComboBox.getValue();
        
        if(instrumentType == null || instrumentBrand == null || maxCost == null || warehouse == null) {
        	showMessage("Invalid Query");
        }
        else {
        out.println(instrumentType + "@" + instrumentBrand + "@" + maxCost + "@" + warehouse);
        out.flush();
        StringBuilder serverResponse = new StringBuilder();
        String line;
        while (!(line = in.nextLine()).equals("END_OF_RESPONSE")) {
            serverResponse.append(line).append("\n");
        }
        showMessage(serverResponse.toString());
        }
    }

    //Main for the client/view
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
        
		Scene scene = new Scene(root, 500, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
