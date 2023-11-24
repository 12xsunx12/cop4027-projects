package project5;

import javafx.application.Application;

public class LaunchMVC {
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);
		
	    view.launch(View.class, args);
	}
}
