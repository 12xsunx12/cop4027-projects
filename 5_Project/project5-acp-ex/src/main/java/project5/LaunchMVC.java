package project5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LaunchMVC extends Application{
	
	@Override
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		Model model = new Model();
		View view = new View();
		Scene scene = new Scene(view.getRoot(), 500, 350); // Create the scene with the root from view
        primaryStage.setTitle("Instrument Request");
        primaryStage.setScene(scene);
        primaryStage.show();
		Controller controller = new Controller(model, view);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
