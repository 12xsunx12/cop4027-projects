import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Editor");

        // Create a resizable TextArea
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);

        // Allow the TextArea to grow horizontally and vertically
        textArea.prefWidthProperty().bind(primaryStage.widthProperty());
        textArea.prefHeightProperty().bind(primaryStage.heightProperty());

        // Create a StackPane layout and add the TextArea to it
        StackPane root = new StackPane();
        root.getChildren().add(textArea);

        // Create a scene with the StackPane as the root and set it on the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        // Add a menu item to save the text
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        primaryStage.setOnCloseRequest(event -> {
            // Save the text when the application is closed
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                saveTextToFile(textArea.getText(), file);
            }
        });

        primaryStage.show();
    }

    private void saveTextToFile(String text, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
