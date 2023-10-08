import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text File Viewer");

        // Create a TextArea to display the file contents
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        // Create a FileChooser to select .txt files
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Create a button to open the file
        StackPane root = new StackPane();
        root.getChildren().add(textArea);

        // Set the event handler for opening a file
        fileChooser.setInitialDirectory(new File("/home/sun/eclipse-workspace/ACP_2_Project/src"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Set the event handler for opening a file
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder content = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                textArea.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
