import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server extends Thread {
	private final int PORT_NUMBER = 9999; // Port that the client will try to connect on
	
	/*
	 * Create a InputStream between Client and Server
	 */
	private BufferedReader establishInputFromClient(Socket clientSocket) throws IOException {
		return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	/*
	 * Create a OutputStream between Client and Server
	 */
	private PrintWriter establishOutputTooClient(Socket clientSocket) throws IOException {
		return new PrintWriter(clientSocket.getOutputStream(), true);
	}
	
	 // Method to handle a single client connection
    private void handleClient(Socket clientSocket) {
        try (PrintWriter writer = establishOutputTooClient(clientSocket); BufferedReader reader = establishInputFromClient(clientSocket)) {
            while (true) {
            	
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close resources when the client disconnects
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
	
	/*
	 * Constructor
	 */
	public Server() {
		start();
	}
	
	/*
	 * Code inside of our thread
	 */
	@Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            System.out.println("Server is listening on port " + PORT_NUMBER);

            while (true) {
                final Socket clientSocket = serverSocket.accept(); // wait for a client to connect
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(new Runnable() { // start new thread for the connected client
                    @Override
                    public void run() {
                        handleClient(clientSocket);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void main (String[] args) {
		 Server server = new Server();
	}
}
