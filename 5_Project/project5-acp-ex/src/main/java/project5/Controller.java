package project5;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;

/*
 * CONTROLLER - handles all netcode and interactions between model and view
 * 
 * - Code Formatting (in this order)
 * 		- variable declarations
 * 		- public constructor();
 * 		- public methods();
 * 		- private methods();
 * 		- public toString();
 */
public class Controller {
	private Model model;
	private View view;
	
	/*
	 * Constructor
	 */
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		
		view.getSubmitButton().setOnAction(e -> handle());
	}
	
	/*
	 * grabs information from both the model and the view to combine their logic
	 */
	private void handle() {
		String instrumentType = view.getInstrumentTypeComboBox();
		String instrumentBrand = view.getInstrumentBrandComboBox();
		String maxCost = view.getMaxCostTextField();
		String warehouseLocation = view.getWarehouseComboBox();
		String resultString = "";
		String finalStringToDisplay = "";
		ResultSet results = model.searchDB(instrumentType, instrumentBrand, maxCost, warehouseLocation);
		
		if (results != null) { // Handle the results
		    try {
		        while (results.next()) {
		            // Construct a string for each row with the required data
		            resultString = results.getString("instName") + " " +
		                                  results.getString("descrip") + " " +
		                                  results.getDouble("cost") + " " +
		                                  results.getInt("quantity") + " " +
		                                  results.getString("address");

		            finalStringToDisplay += "\n\n" + resultString;
		        }
		        
		        // Send string to message popup box; print information
		        view.showMessage(finalStringToDisplay);
		    } catch (SQLException e) {
		        System.out.println("Error processing search results: " + e.getMessage());
		    }
		} else {
		    System.out.println("No results found or an error occurred.");
		}

	}
	
}
