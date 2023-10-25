import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class RaceGui extends Application {
	private BorderPane root;
	private HBox buttonPane;
	private VBox horsePane;
	
	private Canvas horseCanvas;
	
	private Canvas CreateHorseCanvas() {
		Canvas canvas = new Canvas(2000,150);
		return canvas;
	}
	
	private Canvas DrawHorse() {
		Canvas canvas = CreateHorseCanvas();
		GraphicsContext graphic = canvas.getGraphicsContext2D();
		graphic.fillRect(0, 0, 100, 50);
		return canvas;
	}
	
	private Canvas AnimateHorse(Canvas horseCanvas) {
		GraphicsContext graphic = horseCanvas.getGraphicsContext2D();
		graphic.clearRect(0, 0, horseCanvas.getWidth(), horseCanvas.getHeight());
		graphic.translate(10, 0);
		graphic.fillRect(0, 0, 100, 50);
		return horseCanvas;
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		root = new BorderPane();
		horsePane = new VBox();
		Canvas horse0 = DrawHorse();

		horsePane.getChildren().add(horse0);
		
		root.setTop(buttonPane);
		root.setLeft(horsePane);
		
		//Create Scene, set size, set pane, and make visible
		Scene mainScene = new Scene(root, 500, 400);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
