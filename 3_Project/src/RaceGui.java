import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.stage.Stage;

public class RaceGui extends Application {
	private BorderPane root;
	private HBox buttonPane;
	private VBox horsePane;
	private HorseRace horseRace;
	
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
		horsePane = new VBox();
		horseRace = new HorseRace();

		// Add horses to pane
		Horse[] horses = horseRace.GetHorses();
		for (int i = 0; i < horses.length; i++) {
			horsePane.getChildren().add(horses[i]);
		}
		
		//root.setTop(buttonPane);
		root.setTop(buttonPane);
		root.setLeft(horsePane);

		// Start the race
		horseRace.StartRace();
		
		
		//Create Scene, set size, set pane, and make visible
		Scene mainScene = new Scene(root, 1000, 600);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
