package project5;

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
		 System.out.println("Instrument Type: " + view.getInstrumentTypeComboBox());
		 System.out.println("Instrument Brand: " + view.getInstrumentBrandComboBox());
		 System.out.println("Maximum Cost: " + view.getMaxCostTextField());
		 System.out.println("Warehouse Location: " + view.getWarehouseComboBox());
	}
	
	
}
