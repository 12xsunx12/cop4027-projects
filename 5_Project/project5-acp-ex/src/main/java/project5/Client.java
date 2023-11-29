package project5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
	
	private View view;
	
	public static void main(String[] args) throws IOException{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final int SBAP_PORT = 8888;
	    Socket s = new Socket("localhost", SBAP_PORT);
	    
	    view = new View();
		Scene scene = new Scene(view.getRoot(), 500, 350); // Create the scene with the root from view
        primaryStage.setTitle("Instrument Request");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public View getView() {
		return view;
	}
	
}
