import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	private final int PORT_NUMBER = 9999; // Port that the client will try to connect on
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	/*
	 * Create the ServerSocket object
	 */
	private ServerSocket createServerSocket() {
		try {
			return serverSocket = new ServerSocket(PORT_NUMBER);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * Puts server into listen mode. The next client that connects is recognized and taken to a new thread where the game is played
	 */
	private void acceptConnection() {
		try {
			System.out.println("Listening for connection...");
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Private class used to manage the actual game itself within each thread.
	 */
	private class ClientHandler implements Runnable {
		private Socket clientSocket;
		
		/*
		 * Create the buffered reader needed for client -> server (input)
		 */
		private BufferedReader createBufferedReader() {
			try {
				return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		
		/*
		 * Create the print writer needed for server -> client (output)
		 */
		private PrintWriter createPrintWriter() {
			try {
				return new PrintWriter(clientSocket.getOutputStream());
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		
		/*
		 * Constructor
		 */
		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		/*
		 * The actual code for playing tictactoe is located here
		 */
		@Override
		public void run() {
		    try {
		        BufferedReader clientReader = createBufferedReader();
		        PrintWriter clientWriter = createPrintWriter();
		        
		        String messageFromClient = "";
		        while ((messageFromClient = clientReader.readLine()) != null) {
		            System.out.println("Received from client: " + messageFromClient);
		            clientWriter.println(messageFromClient);
		            clientWriter.flush(); // Flush the output stream
		        }
		    } catch (IOException e) {
		        System.out.println(e.getMessage());
		    }
		}
	}
	
	/*
	 * Constructor
	 */
	public Server() {
		serverSocket = createServerSocket();
	}
	
	/*
	 * All program logic goes in this method. Each thread will run whatever this is seperatetly.
	 */
	 @Override
	    public void run() {
	        while (true) {
	        	// Logic initially ran to accept new clients
	            acceptConnection();
	            System.out.println("New client connected: " + clientSocket.getInetAddress());
	            new Thread(new ClientHandler(clientSocket)).start();;
	        }
	    }
	
	public static void main (String[] args) {
		new Thread(new Server()).start();
	}
}
	
	
