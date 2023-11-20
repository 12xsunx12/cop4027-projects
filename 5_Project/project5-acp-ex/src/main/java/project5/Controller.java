package project5;

/*
 * CONTROLLER - handles all netcode and interactions between model and view
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
	}
}
