import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WordEditor extends Application {
	
	// The physical window everything will go inside of
	Scene mainScene;
	
	// Layout I am using set as a constant
	BorderPane root;
	
	// Creates a menu bar at the top
	MenuBar menuBar;
	
	// A menu option that goes inside the menuBar
	Menu menuFile;
	
	// Items that go inside of a Menu object; drop down menu items
	MenuItem newItem, openItem, saveItem, exitItem;
	
	// File Chooser used to select files
	FileChooser fileChooser;
	
	// TextArea creates a place to type / input
	TextArea textArea;
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Text Editor");
		
		root = new BorderPane();
		menuBar = new MenuBar();
		menuFile = new Menu("File");	
		textArea = new TextArea();
		fileChooser = new FileChooser();
		
		// Configuring the text area
		textArea.setWrapText(true);
		textArea.prefWidthProperty().bind(mainStage.widthProperty());
        textArea.prefHeightProperty().bind(mainStage.heightProperty());
		
        // Configurnig the fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File("/home/sun/eclipse-workspace/ACP_2_Project/src"));                
		
		// Creating menu items + Event handling
		newItem = createItem("New", new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				textArea.clear();
			}
		});
		newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		
		openItem = createItem("Open", new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				openFile(mainStage);
			}
		});
		openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		
		saveItem = createItem("Save", new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(mainStage);
				if (file != null) {
					saveTextToFile(textArea.getText(), file);
				}
			}
		});
		saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		
		exitItem = createItem("Exit", new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        
		// Add items to menu(s)
		menuFile.getItems().addAll(newItem, openItem, saveItem, exitItem);
		menuBar.getMenus().addAll(menuFile);
		
		// Add to scene
		root.setTop(menuBar);
		root.setCenter(textArea);
		
		// Set scene and make visible
		mainScene = new Scene(root, 500, 400);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	private MenuItem createItem(String name, EventHandler<ActionEvent> action) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(action);
		return item;
	}
	
	private void saveTextToFile(String text, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void openFile(Stage mainStage) {
		File file = fileChooser.showOpenDialog(mainStage);
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
