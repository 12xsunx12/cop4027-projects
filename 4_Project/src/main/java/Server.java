import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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
		private int[][] board; // the tictactoe board
		
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
		 * returns a random number 0(inclusive) - 3(exclusive)
		 */
		private String generateRandomMove() {
	        Random rand = new Random();
	        String column = Integer.toString(rand.nextInt(3));
	        String row = Integer.toString(rand.nextInt(3));
	        return column + row;
	    }
		
		/*
		 * Constructor
		 */
		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
			
			board = new int[3][3];
			
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					board[i][j] = 0;
				}
			}
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
		            return true;
		        }
		    }
		    
		    // Check diagonals
		    if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) || 
		        (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
		        return true;
		    }
		    
		    return false;
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
		        String messageTooClient = "";
		        
		        while (true) {
		        	messageTooClient = generateRandomMove();
		        	while(updateBoard(messageTooClient, true) == false || messageTooClient == null) {
		        		messageTooClient = generateRandomMove();
		        	}
		        	clientWriter.println(messageTooClient);
		        	clientWriter.flush();
		        	updateBoard(messageTooClient, true);
		        	drawBoard();
		        	messageFromClient = clientReader.readLine();
		        	if (messageFromClient.equals("q")) break; // client has declared a winner or loser and the server will exit
		        	updateBoard(messageFromClient, false);
		        	drawBoard();
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
	
	
