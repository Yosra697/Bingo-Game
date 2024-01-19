package BingoGameGatewayPackage;

import java.io.IOException; 
import java.net.ServerSocket;
import java.net.Socket;

public class BingoGateway {

    public static void main(String[] args) throws IOException {
        // ServerSocket Initialization
        ServerSocket serverSocket = new ServerSocket(55556);
        System.out.println("Gateway is running and waiting for connections...");

        // Infinite Loop for Handling Connections
        while (true) {
            // Accept incoming client connections
            Socket clientSocket = serverSocket.accept();

            // Start a new thread to handle each client connection
            //new Thread(new BingGatewayHandlerRPC(clientSocket)).start();
            new Thread(new BingGatewayHandler(clientSocket)).start();
        }
    }
}
