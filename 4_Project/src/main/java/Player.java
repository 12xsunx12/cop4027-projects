import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * The player class represents a client connecting to a server hosting TicTacToe
 * player can send moves to the server and the server will respond with a move
 */
public class Player {
	private final int PORT_NUMBER = 9999; // Port that the client will try to connect on
	private final String IP_ADDRESS = "127.0.0.1"; // address of the machine we are connecting too (local host)
	
	private Socket socket; // a "socket" is an endpoint to send or recieve data between two computers using TCP/IP protocols. They use InputStream and OutputStream to read/write to each other.

	private BufferedReader readerServer; // Reads messages from the server
	private BufferedReader readerClient; // Reads messages from YOU, not the server, but whatever you type in the terminal
	private PrintWriter writer; // Automatically produces formatted text. Essentially a more fancy ouputStream
	
	/*
	 * Try to create an endpoint (socket) between Client and Server
	 * This connection is then set as our private 'socket' object
	 */
	private void establishConnection() {
		try {
			socket = new Socket(IP_ADDRESS, PORT_NUMBER); // Attempt to create a connection between Client and Server
			System.out.println("Client has successfully connected too: " + IP_ADDRESS + " on port: " + PORT_NUMBER);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage()); // Couldn't find the host
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e.getMessage()); // Input or Ouput stream got all wack
			System.exit(1);
		}
	}
	
	/*
	 * Try to create the 'readerServer' obj by setting it equal to a new BufferedReader; InputStreamReader as input created from the socket's InputStream
	 */
	private void establishInputFromServer() {
		try {
			readerServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Client ServerReader On");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Try to create the 'readerClient' obj by setting it equal to a new BufferedReader; InputStreamReader as input created from keyboard
	 */
	private void establishInputFromClient() {
		readerClient = new BufferedReader(new InputStreamReader(System.in));
		if (readerClient != null) {
			System.out.println("Client ClientReader On");
		}
	}
	
	/*
	 * Try to create the 'writer' obj by setting it equal to a new PrintWriter taking the socket's OutputStream as input
	 */
	private void establishOutputTooServer() {
		try {
			writer = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Client Writer On");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Runs all functions related to input and output between Client and Server
	 */
	private void establishCommmunication() {
		establishInputFromServer();
		establishInputFromClient();
		establishOutputTooServer();
	}
	
	/*
	 * Constructor
	 */
	public Player() {
		establishConnection();
		establishCommmunication();
	}
	
	/*
	 * Returns a string; a message sent to the client from the server
	 */
	public String readServer() {
		try {
			return readerServer.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * Returns a string; whatever you type in the console when this method is called
	 */
	public String readClient() {
		try {
			return readerClient.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * Send a message to the server
	 */
	public void write(String message) {
		writer.println(message);
	}

    public static void main(String[] args) {
        Player client = new Player();
    }
}