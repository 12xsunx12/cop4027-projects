/***************************************************************
Student Name: Trent Wells, Regan O'Donnell
File Name: Controller.java
Assignment number: Project 5

***************************************************************/
package project5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller implements Runnable{
	private Model model;
	private Scanner in;
	private PrintWriter out;
	private Socket s;
	
	//Constructor
	public Controller(Model model, Socket s) {
		this.model = model;
		this.s = s;
	}
	
	//Main for controller/server
	public static void main(String[] args) throws IOException{  
	    final int SBAP_PORT = 8888;
	    ServerSocket server = new ServerSocket(SBAP_PORT);
	    System.out.println("Waiting for clients to connect...");
	    while (true){
	    	Socket s = server.accept();
	        System.out.println("Client connected.");
	        Controller controller = new Controller(new Model(), s);
            Thread t = new Thread(controller);
	        t.start();
	    }
	}

	//Run method for the thread created in main
	@Override
	public void run() {
		try{
			try{
				in = new Scanner(s.getInputStream());
				out = new PrintWriter(s.getOutputStream());
				doService();            
			}
			finally{
				s.close();
			}
		}
		catch (IOException exception){
			exception.printStackTrace();
		}
	}
	
	//This method is called to handle the messages between the model and view
	public void doService() throws IOException {
		while (in.hasNextLine()) {
		    String response = in.nextLine();
		    System.out.println(response);
		    ResultSet results = model.searchDB(response);
		    String finalResultStringSentTooMessageBox = "";

		    if (results != null) {
		        try {
		            while (results.next()) {
		                String resultString = results.getString("instName") + " " +
		                        results.getString("descrip") + " " +
		                        results.getDouble("cost") + " " +
		                        results.getInt("quantity") + " " +
		                        results.getString("address");

		                finalResultStringSentTooMessageBox += resultString + "\n\n";
		            }
		            out.println(finalResultStringSentTooMessageBox);
		            out.println("END_OF_RESPONSE");
		            out.flush();
		        } catch (SQLException e) {
		            System.out.println("Error processing search results: " + e.getMessage());
		        }
		    } else {
		        System.out.println("No results found or an error occurred.");
		    }
		}

	}

}
