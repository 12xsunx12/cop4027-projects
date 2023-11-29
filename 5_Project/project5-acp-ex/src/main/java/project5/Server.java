package project5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	
	Client client;
	
	public static void main(String[] args) throws IOException{  
	    final int SBAP_PORT = 8888;
	    ServerSocket server = new ServerSocket(SBAP_PORT);
	    System.out.println("Waiting for clients to connect...");
	    while (true){
	    	Socket s = server.accept();
	        System.out.println("Client connected.");
	        Thread t = new Thread();	
	        t.start();
	    }
	}

	@Override
	public void run() {
		client = new Client();
		Model model = new Model();
		Controller controller = new Controller(model, client.getView());
		
	}
}
