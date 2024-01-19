package RPCBingoGameServerPackage;

import java.io.IOException; 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BingoServiceImplRPC extends UnicastRemoteObject implements BingoService,Runnable {

    private static final long serialVersionUID = 1L;
    private static final int MAX_TURNS = 10;
    private List<Integer> urne = new ArrayList<>();
    private List<Integer> bestScores;
    private int correctPredictions = 0;
    private final Socket clientSocket;
    public BingoServiceImplRPC(Socket clientSocket) throws RemoteException {
        initializeUrne();
        bestScores = new ArrayList<>();
        this.clientSocket=clientSocket;
    }
    @Override
    public void recordScore(int score) throws RemoteException {
        bestScores.add(score);
    }

    @Override
    public boolean isUrneEmpty() throws RemoteException {
        return urne.isEmpty();
    }
    private void initializeUrne() {
        urne.clear();
        for (int i = 0; i < MAX_TURNS; i++) {
            urne.add(i+1);
        }
        Collections.shuffle(urne, new Random()); // Use Random for shuffling
    }

    @Override
    public String playBingo(int prediction) throws RemoteException {
        int playerScore = calculatePlayerScore();
        
        String fin="";
        int drawnNumber = urne.remove(0);
        
        if (prediction == drawnNumber) {
            correctPredictions++;
            if (isUrneEmpty()) {
                recordScore(playerScore+1);
                correctPredictions = 0;
                initializeUrne();
                fin= "\nUrne vide. La partie est terminée.";
            }
            return "Bravo ! Prédiction correcte. Numéro tiré : " + drawnNumber + ". Votre score est : " + playerScore + "/10."+fin;
        } else {
        	if (isUrneEmpty()) {
                recordScore(playerScore);
                correctPredictions = 0;
                initializeUrne();
                fin= "\nUrne vide. La partie est terminée.";
            }
            return "Dommage. Prédiction incorrecte. Numéro tiré : " + drawnNumber+fin;
        }
    }

    private int calculatePlayerScore() {
        return correctPredictions;
    }

    @Override
    public String getBestScore() throws RemoteException {
        if (bestScores.isEmpty()) {
            return "Aucun meilleur score enregistré.";
        }

        int maxScore = Collections.max(bestScores);
        return "Meilleur score : " + maxScore + "/10";
    }

    

    @Override
    public String connect(String id) throws RemoteException {
      
            return("Connection done ");	
         
    }
    @Override
    public void run() {
    	try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
			BingoServiceImpl bingoService = new BingoServiceImpl();
			String methodName;
			while(true) {
			methodName = (String) in.readObject();

			// Execute the appropriate method on the server based on the RPC method name
			switch (methodName) {
			case "bestScore":
				String result = "" + bingoService.getBestScore();
				System.out.println(result);
				out.writeObject(result);
				break;
			case "Round":
				int num = (int) in.readObject();
				System.out.println(num);
				result = bingoService.playBingo(num);
				System.out.println("hedhi resultat" + result);
				out.writeObject(result);
			default:
				System.out.println("Unknown RPC method: " + methodName);
				break;
			}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    }

