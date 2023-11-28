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
 * 		- private methods();
 * 		- public methods();
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
	
	private void handle() {
		String instrumentType = view.getInstrumentTypeComboBox();
		String instrumentBrand = view.getInstrumentBrandComboBox();
		String maxCost = view.getMaxCostTextField();
		String warehouseLocation = view.getWarehouseComboBox();
		//model.select(instrumentType, instrumentBrand, maxCost, warehouseLocation);
		ResultSet results = model.searchDB(instrumentType, instrumentBrand, maxCost, warehouseLocation);
//
//	    // Handle the results
//	    if (results != null) {
//	        try {
//	            while (results.next()) {
//	                // Process each row of the result set as per your requirement
//	                // For example, you can print out the values or add them to a list
//	                System.out.println("Instrument Name: " + results.getString("instName"));
//	                // Add other columns as needed
//	            }
//	        } catch (SQLException e) {
//	            System.out.println("Error processing search results: " + e.getMessage());
//	        }
//	    } else {
//	        System.out.println("No results found or an error occurred.");
//	    }
		// Handle the results
		if (results != null) {
		    try {
		        while (results.next()) {
		            // Construct a string for each row with the required data
		            String resultString = results.getString("instName") + " " +
		                                  results.getString("descrip") + " " +
		                                  results.getDouble("cost") + " " +
		                                  results.getInt("quantity") + " " +
		                                  results.getString("address");

		            // Print the string
		            System.out.println(resultString);
		        }
		    } catch (SQLException e) {
		        System.out.println("Error processing search results: " + e.getMessage());
		    }
		} else {
		    System.out.println("No results found or an error occurred.");
		}

	}
	
}
