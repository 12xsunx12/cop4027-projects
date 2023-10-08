import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class MenuEventDemo extends Application {
 
    @Override
    public void start(Stage stage) {
 
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
 
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
 
        // Create MenuItems
        
        MenuItem newItem = createNewItem();
        MenuItem openFileItem = createOpenFileItem();  
        MenuItem exitItem = createExitItem();    
  
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(newItem, openFileItem, exitItem);
 
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
 
        BorderPane root = new BorderPane();
        // comment this to see that menu bar actually goes in the scene space
        root.setTop(menuBar);
        Scene scene = new Scene(root, 350, 200);
       
        stage.setTitle("JavaFX Menu Coffey Edits");
        stage.setScene(scene);
        stage.show();
    }
 
    
    private MenuItem createNewItem()
    {
        MenuItem newItem = new MenuItem("New");
        
        newItem.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Chose the file-new item");
            }
        });     
        return newItem;
     }
 
     private MenuItem createOpenFileItem()
     {
        MenuItem openFileItem = new MenuItem("Open File");
        openFileItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Chose the file-open item");
            }
        });       
        return openFileItem;
     }

     private MenuItem createExitItem()
     {
        MenuItem exitItem = new MenuItem("Exit");
         // Set Accelerator for Exit MenuItem.
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        // When user click on the Exit item
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        return exitItem;
     }

 
    public static void main(String[] args) {
        Application.launch(args);
    }
 
}