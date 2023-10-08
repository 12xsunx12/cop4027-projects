import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SpellChecker extends Application {
	
	Scene mainScene;
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Text Editor");
				
		
		// Create scene and make visible
		mainScene = new Scene(new StackPane(), 200, 100);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
