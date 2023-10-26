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
		Horse horse1 = new Horse();
		Horse horse2 = new Horse();
		Horse horse3 = new Horse();
		Horse horse4 = new Horse();
		Horse horse5 = new Horse();
		Horse horse6 = new Horse();

		horsePane.getChildren().addAll(horse1,horse2,horse3,horse4,horse5,horse6);
		
		//root.setTop(buttonPane);
		root.setLeft(horsePane);
		
		Thread horse1_t = new Thread(horse1);
		Thread horse2_t = new Thread(horse2);
		Thread horse3_t = new Thread(horse3);
		Thread horse4_t = new Thread(horse4);
		Thread horse5_t = new Thread(horse5);
		Thread horse6_t = new Thread(horse6);
		
		horse1_t.start();
		horse2_t.start();
		horse3_t.start();
		horse4_t.start();
		horse5_t.start();
		horse6_t.start();
		
		//Create Scene, set size, set pane, and make visible
		Scene mainScene = new Scene(root, 1000, 400);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
