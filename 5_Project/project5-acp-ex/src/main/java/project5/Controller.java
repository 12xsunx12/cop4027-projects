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
	
	public Controller(Model model, Socket s) {
		this.model = model;
		this.s = s;
	}
	
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
	
	public void doService() throws IOException {
		// Inside doService() method in Controller class
		while (in.hasNextLine()) {
		    String response = in.nextLine();
		    System.out.println(response);
		    ResultSet results = model.searchDB(response);
		    String finalResultStringSentTooMessageBox = "";

		    if (results != null) {
		        try {
		            while (results.next()) {
		                // Construct a string for each row with the required data
		                String resultString = results.getString("instName") + " " +
		                        results.getString("descrip") + " " +
		                        results.getDouble("cost") + " " +
		                        results.getInt("quantity") + " " +
		                        results.getString("address");

		                finalResultStringSentTooMessageBox += resultString + "\n\n";
		            }

		            // Send the constructed message back to the client
		            out.println(finalResultStringSentTooMessageBox);
		            out.println("END_OF_RESPONSE"); // Mark the end of response
		            out.flush(); // Ensure the message is sent
		        } catch (SQLException e) {
		            System.out.println("Error processing search results: " + e.getMessage());
		        }
		    } else {
		        System.out.println("No results found or an error occurred.");
		    }
		}

	}

}
