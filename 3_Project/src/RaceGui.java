import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class RaceGui extends Application {
	private BorderPane root;
	private HBox buttonPane;
	private VBox horsePane;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		root = new BorderPane();
		buttonPane = new HBox();
		horsePane = new VBox();
		Horse horse = new Horse();
		Horse horse1 = new Horse();

		horsePane.getChildren().addAll(horse,horse1);
		
		//root.setTop(buttonPane);
		root.setLeft(horsePane);
		
		horse.AnimateHorse();
		horse1.AnimateHorse();
		
		//Create Scene, set size, set pane, and make visible
		Scene mainScene = new Scene(root, 1000, 400);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
