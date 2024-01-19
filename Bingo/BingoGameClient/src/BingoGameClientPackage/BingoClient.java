package BingoGameClientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class BingoClient implements Serializable {
    private static final long serialVersionUID = 1L;
    public static void main(String[] args) {
        try {
        	
            // Socket Initialization
            Socket socket = new Socket("localhost", 55556);
            int option = 0;
            BufferedReader reader ; 
            PrintWriter writer ; 
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Connect") ; 
            writer.println(UUID.randomUUID().toString() ) ; 
            while (option != 3) {
                // Menu
                displayMenu();

                // User Input for Menu Option
                Scanner scanner = new Scanner(System.in);
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                    	int round = 0 ; 
                        // Input/Output Setup
                        reader = new BufferedReader(new InputStreamReader(System.in));
                        writer = new PrintWriter(socket.getOutputStream(), true);
                        playBingo(socket, reader, writer , round);
                        break;
                    case 2:
                        getBestScore(socket);
                  	    System.out.println("**********************************************");  
                        break;
                    case 3:
                    	writer = new PrintWriter(socket.getOutputStream(), true);
                    	writer.println("Option 3 " );
                  	    System.out.println("**********************************************");  
                        System.out.println("Exiting Bingo Game. Goodbye!");
                  	    System.out.println("**********************************************");  
                        break;
                    default:
                        System.out.println("Invalid option.");
                  	    System.out.println("**********************************************");  
                        displayMenu();
                }
            }

            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Bingo Game Menu");
        System.out.println("1. Jouer BINGO");
        System.out.println("2. Connaître le meilleur score");
        System.out.println("3. Quitter");
        System.out.print("Enter your choice (1-3): ");
    }

    private static void playBingo(Socket socket, BufferedReader reader, PrintWriter writer , int round) throws IOException {
        BufferedReader resultReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	    System.out.println("Let's play BINGO!");
    	    String result = "" ; 
    	    while((! result.contains("Urne vide. La partie est terminée.")) && round<10)
    	    {   round++ ; 
                System.out.println("Round " + round);
                System.out.print("Enter your prediction (1-10): ");
                int prediction = Integer.parseInt(reader.readLine());

                // Send the prediction to the server
                writer.println(prediction);

               // Receive and print the result
               result = resultReader.readLine();
               System.out.println(result);    
    	    }
    	    if (resultReader.ready()) resultReader.readLine();
    	    System.out.println("**********************************************");   
            }

    private static void getBestScore(Socket socket) throws IOException {

        // Input/Output Setup
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
         writer.println("Option 2 " );
        // Receive and print the best score
        String bestScore = reader.readLine();
        System.out.println("Best Score: " + bestScore);
    }
}