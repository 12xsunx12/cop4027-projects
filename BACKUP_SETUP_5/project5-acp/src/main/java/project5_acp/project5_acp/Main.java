package project5_acp.project5_acp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a TextField
        TextField textField = new TextField();
        textField.setPromptText("Type here and press Enter");

        // Event handler for the Enter key
        textField.setOnAction(e -> {
            String enteredText = textField.getText();
            System.out.println("Entered: " + enteredText);
            textField.clear();
        });

        // Create a layout and add the TextField
        VBox root = new VBox();
        root.getChildren().add(textField);

        // Create a scene
        Scene scene = new Scene(root, 300, 200);

        // Set the scene and show the stage
        primaryStage.setTitle("Text Box Input");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

