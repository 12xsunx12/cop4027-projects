import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class RaceGui extends Application {
	private BorderPane root;
	private HBox buttonPane;
	private VBox horsePane;
	private HorseRace horseRace;
	private Button startButton, resetButton, quitButton;
	private Stage mainStage;
	
	private Button CreateStartButton() {
		Button button = new Button("Start Race");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent a) {
				horseRace.StartRace();
				button.setDisable(true);
			}
		});
		
		return button;
	}
	
	private Button CreateResetButton() {
		Button button = new Button("Reset");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent a) {
				horseRace.ResetRace();
				startButton.setDisable(false);
	            mainStage.close();
	            Stage newStage = new Stage();
	            try {start(newStage);} catch (Exception e) {e.printStackTrace();}
			}
		});
		
		return button;
	}
	
	private Button CreateQuitButton() {
		Button button = new Button("Quit");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent a) {
				System.exit(0);
			}
		});
		
		return button;
	}
	
	private HBox CreateButtonPane() {
		HBox hbox = new HBox();
		
		startButton = CreateStartButton();
		resetButton = CreateResetButton();
		quitButton = CreateQuitButton();
		
		hbox.getChildren().addAll(startButton,resetButton,quitButton);
		
		return hbox;
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		this.mainStage = mainStage;
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

		
		//Create Scene, set size, set pane, and make visible
		Scene mainScene = new Scene(root, 1000, 600);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
