package project5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.event.ActionEvent;

public class Controller implements Runnable{
	private Model model;
	private Scanner in;
	private PrintWriter out;
	private Socket s;
	
	public Controller(Model model, Socket s) {
		this.model = model;
		this.s = s;
	}
	
//	public void handle() {
//		String instrumentType = view.getInstrumentTypeComboBox();
//		String instrumentBrand = view.getInstrumentBrandComboBox();
//		String maxCost = view.getMaxCostTextField();
//		String warehouseLocation = view.getWarehouseComboBox();
//		String resultString = "";
//		String finalStringToDisplay = "";
//		ResultSet results = model.searchDB(instrumentType, instrumentBrand, maxCost, warehouseLocation);
//		
//		if (results != null) { // Handle the results
//		    try {
//		        while (results.next()) {
//		            // Construct a string for each row with the required data
//		            resultString = results.getString("instName") + " " +
//		                                  results.getString("descrip") + " " +
//		                                  results.getDouble("cost") + " " +
//		                                  results.getInt("quantity") + " " +
//		                                  results.getString("address");
//
//		            finalStringToDisplay += "\n\n" + resultString;
//		        }
//		        
//		        // Send string to message popup box; print information
//		        view.showMessage(finalStringToDisplay);
//		    } catch (SQLException e) {
//		        System.out.println("Error processing search results: " + e.getMessage());
//		    }
//		} else {
//		    System.out.println("No results found or an error occurred.");
//		}
//
//	}
	
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
	
	public void doService() throws IOException{  
		while (in.hasNextLine() != false) {
			String response = in.nextLine();
			System.out.println(response);
			ResultSet results = model.searchDB(response);
			if (results != null) {
			    try {
			        while (results.next()) {
			            // Construct a string for each row with the required data
			            String resultString = results.getString("instName") + " " +
			                                  results.getString("descrip") + " " +
			                                  results.getDouble("cost") + " " +
			                                  results.getInt("quantity") + " " +
			                                  results.getString("address");

			            // Print the string
			            System.out.println(resultString);
			        }
			    } catch (SQLException e) {
			        System.out.println("Error processing search results: " + e.getMessage());
			    }
			} else {
			    System.out.println("No results found or an error occurred.");
			}
		}
	}
}
