import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RaceGui extends Application {
	private BorderPane root;
	private HBox buttonPane;
	
	private HBox CreateButtonPane() {
		HBox hbox = new HBox();
		
		Button runButton = new Button("Run");
		Button resetButton = new Button("Reset");
		Button quitButton = new Button("Quit");
		
		hbox.getChildren().addAll(runButton,resetButton,quitButton);
		
		return hbox;
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		root = new BorderPane();
		buttonPane = CreateButtonPane();
		
		root.setTop(buttonPane);
		
		//Create Scene, set size, set pane, and make visible
		Scene mainScene = new Scene(root, 500, 400);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
