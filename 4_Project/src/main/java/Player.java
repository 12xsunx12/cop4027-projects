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
	
	private int[][] board; // the tictactoe board
	
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
			if (readerServer != null) {
				System.out.println("Client ServerReader On");
			}
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
			if (writer != null) {
				System.out.println("Client Writer On");
			}
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
		board = new int[3][3];
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = 0;
			}
		}
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
	
	/*
	 * Draw the board in the terminal
	 */
	public void drawBoard() {
		int currentSlot = 0;
		
		System.out.print("\n\n\n");
		
		for (int i = 0; i < 3; i++) {
			System.out.print("\n");
			for (int j = 0; j < 3; j++) {
				currentSlot = board[i][j];
				
				if (currentSlot == 0) {
					System.out.print("E  ");
				} else if (currentSlot == 1) {
					System.out.print("O  ");
				} else if (currentSlot == 2) {
					System.out.print("X  ");
				}
			}
		}
		
		System.out.print("\n");
	}
	
	/*
	 * Update the board with the move made by either client or player. This method will place an 'X' or 'O' in the board
	 */
	public boolean updateBoard(String move, boolean clientOrServer) {
		int column = move.trim().charAt(0) - '0';
		int row = move.trim().charAt(1) - '0';
		
		// client
		if ((clientOrServer == false) && (column < 3) && (row < 3) && (column >= 0) && (row >= 0) && (board[column][row] == 0)) {
			board[column][row] = 1;
			return true;
		// server
		} else if ((clientOrServer == true) && (column < 3) && (row < 3) && (column >= 0) && (row >= 0) && (board[column][row] == 0)) { 
			board[column][row] = 2;
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Checks to see if any of the rows or columns have three in a row, if so, returns true
	 */
	private boolean checkWinner(int player) {
	    // Check rows and columns
	    for (int i = 0; i < 3; i++) {
	        if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || 
	            (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
	            if (board[i][0] != 0 && board[i][1] != 0 && board[i][2] != 0) {
	                return true;
	            }
	        }
	    }
	    
	    // Check diagonals
	    if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) || 
	        (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
	        if (board[0][0] != 0 && board[1][1] != 0 && board[2][2] != 0) {
	            return true;
	        }
	    }
	    
	    return false;
	}
	
	private boolean noMoreSpaces() {
		int counter = 0;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != 0) {
					counter++;
				}
			}
		}
		
		if (counter >= 9) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
	    Player client = new Player();
	    String playerName = "";
	    String messageTooServer = "";
	    String messageFromServer = "";
	    
	    System.out.print("Please enter a player name: ");
	    playerName = client.readClient();
	    
	    while (true) {	    	
	    	// recieving move from server logic
	    	messageFromServer = client.readServer();
	    	System.out.print("Server: " + messageFromServer);
	    	client.updateBoard(messageFromServer, true);
	    	// check to see if there are any spaces left
	    	if (client.noMoreSpaces()) {
	    		System.out.print("\n\nIt's a tye! Neither you or the server won. Exiting program...\n\n");
	    		client.write("q");
	    		return;
	    	}
	    	// check to see if the client won
	    	if (client.checkWinner(1)) {
	    		System.out.print("\n\nYou won!!! Exiting program...\n\n"); 
	    		client.write("q");
	    		return;
	    	}
	    	client.drawBoard();
	    	
	    	// sending move to server logic
	    	System.out.print(playerName + ": ");
	    	messageTooServer = client.readClient();
	    	// The user input an invalid input that's not acceptable and would blow everything up
	    	while (client.updateBoard(messageTooServer, false) == false) {
	    		System.out.println("Error: your input was not a correct input, try typing it again \n");
	    		messageTooServer = client.readClient();
	    	}
	    	client.drawBoard();
	    	// check to see if the server won
	    	if (client.checkWinner(0)) {
	    		System.out.print("\n\nThe server beat you!!! Exiting program..."); 
	    		client.write("q");
	    		return;
	    	}
	    	client.write(messageTooServer);
	    	client.writer.flush();
	    }
	}
}
