import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.image.Image;
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;

public class RaceGui extends Application {
	private BorderPane root;
	private HBox buttonPane;
	private VBox horsePane;
	
	private File GetHorseFile() {
		File horseImageFile = new File("honse.png");
		if(horseImageFile.exists()) return horseImageFile;
		else return null;
	}
	
	private Image CreateHorseImage() {
		return new Image(GetHorseFile().toURI().toString());
	}
	
	private ImageView CreateHorseImageViewer() {
		return new ImageView(CreateHorseImage());
	}
	
	private HBox CreateButtonPane() {
		HBox hbox = new HBox();
		
		Button runButton = new Button("Run");
		Button resetButton = new Button("Reset");
		Button quitButton = new Button("Quit");
		
		hbox.getChildren().addAll(runButton,resetButton,quitButton);
		
		return hbox;
	}
	
	private VBox CreateHorsePane() {
		
	}
	
	@Override
	public void start(Stage mainStage) throws Exception {
		root = new BorderPane();
		buttonPane = CreateButtonPane();
		horsePane = new VBox();
		
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
