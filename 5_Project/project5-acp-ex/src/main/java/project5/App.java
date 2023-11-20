package project5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
          BorderPane root = new BorderPane();
          Scene scene = new Scene(root, 500, 300);
          stage.setTitle("Testing");
          stage.setScene(scene);
          stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}