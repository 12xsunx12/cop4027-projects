/* @author: Regan O'Donnell
 * @class: COP4027
 * @professor: J. Coffey
 * 
 * Project 2
 * 
 */

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WordEditor extends Application {
	
	// The physical window everything will go inside of
	private Scene mainScene;
	
	// Layout I am using set as a constant
	private BorderPane root;
	
	// Creates a menu bar at the top
	private MenuBar menuBar;
	
	// A menu option that goes inside the menuBar
	private Menu menuFile, menuSpellCheck;
	
	// Items that go inside of a Menu object; drop down menu items
	private MenuItem newItem, openItem, saveItem, exitItem, spellCheckItem;
	
	// File Chooser used to select files
	private FileChooser fileChooser;
	
	// TextArea creates a place to type / input
	private TextArea textArea;
	
	// Spell Checker class checks spelling
	private SpellChecker spellChecker;
	
	@Override
	public void start(Stage mainStage) {
		mainStage.setTitle("Text Editor");
		
		// Creating variables
		root = new BorderPane();
		menuBar = new MenuBar();
		menuFile = new Menu("File");
		menuSpellCheck = new Menu("Check Spelling");
		textArea = new TextArea();
		fileChooser = new FileChooser();
		spellChecker = new SpellChecker();
		
		// Configuring the text area
		textArea.setWrapText(true);
		textArea.prefWidthProperty().bind(mainStage.widthProperty());
        textArea.prefHeightProperty().bind(mainStage.heightProperty());
		
        // Configurnig the fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));                
		
		// Creating menu items + Event handling
		newItem = CreateNewItem("New");
		newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		
		openItem = CreateOpenItem("Open", mainStage);
		openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		
		saveItem = CreateSaveItem("Save", mainStage);
		saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		
		exitItem = CreateExitItem("Exit");
		exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		
		// Creating Spell Check menu button + logic
		spellCheckItem = CreateSpellCheckMenu("Check Spelling", mainStage);
        
		// Add items to menu(s)
		menuFile.getItems().addAll(newItem, openItem, saveItem, exitItem);
		menuSpellCheck.getItems().addAll(spellCheckItem);
		menuBar.getMenus().addAll(menuFile,menuSpellCheck);
		
		// Add to scene
		root.setTop(menuBar);
		root.setCenter(textArea);
		
		// Set scene and make visible
		mainScene = new Scene(root, 500, 400);
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	private MenuItem CreateNewItem(String name) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				textArea.clear();
			}
		});
		return item;
	}
	
	private MenuItem CreateOpenItem(String name, Stage mainStage) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				OpenFile(mainStage);
			}
		});
		return item;
	}
	
	private MenuItem CreateSaveItem(String name, Stage mainStage) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(mainStage);
				if (file != null) {
					SaveTextToFile(textArea.getText(), file);
				}
			}
		});
		return item;
	}
	
	private MenuItem CreateExitItem(String name) {
		MenuItem item = new MenuItem(name);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		return item;
	}
	
	private MenuItem CreateSpellCheckMenu(String name, Stage mainStage) {
		MenuItem item = new Menu(name);
		item.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				int counter = 0;
				String[] wordsInTextArea = textArea.getText().toLowerCase().trim().split(" |,|\\.|\n");
				for (int i = 0; i < wordsInTextArea.length; i++) {
					if (!(spellChecker.CheckSpelling(wordsInTextArea[i]).equals("-1")) && !(wordsInTextArea[i].equals(""))) {
						Alert alert = new Alert(AlertType.INFORMATION, spellChecker.CheckSpelling(wordsInTextArea[i]));
						alert.setHeaderText("Mispelled Word: " + wordsInTextArea[i]);
						alert.showAndWait();
					} else {
						counter++;
					}
				}
				
				if (counter == wordsInTextArea.length) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("All words spelled correctly!");
					alert.showAndWait();
				}
			}
		});
		return item;
	}
	
	private void SaveTextToFile(String text, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void OpenFile(Stage mainStage) {
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
