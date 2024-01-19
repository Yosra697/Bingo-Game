package BingoGameGatewayPackage;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Naming;

import BingoGameServerPackage.BingoService;

public class BingGatewayHandler implements Runnable {

    private Socket clientSocket;

    public BingGatewayHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Lookup the BingoService from RMI Registry
            BingoService stub = (BingoService) Naming.lookup("rmi://localhost:1098/SR");
            String clientMessage = reader.readLine();

            if ("Connect".equals(clientMessage)) {
                String objectId = reader.readLine();
                String testConnect = stub.connect(objectId);

                if (!"Pb de création d'objet".equals(testConnect)) {
                    BingoService stub2 = (BingoService) Naming.lookup(testConnect);
                    System.out.println(stub2);

                    while (true) {
                        clientMessage = reader.readLine();

                        if ("Option 2 ".equals(clientMessage)) {
                            // Handle Option 2 logic
                            writer.println(stub2.getBestScore());
                        } else {
                            try {
                                int choix = Integer.parseInt(clientMessage);
                                System.out.println(choix);
                                writer.println(stub2.playBingo(choix));
                            } catch (NumberFormatException e) {
                                writer.println("Error: Invalid input for Bingo game");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the socket after handling the client
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
