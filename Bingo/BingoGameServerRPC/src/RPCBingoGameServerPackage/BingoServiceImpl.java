package RPCBingoGameServerPackage;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BingoServiceImpl extends UnicastRemoteObject implements BingoService {

    private static final long serialVersionUID = 1L;
    private static final int MAX_TURNS = 10;
    private List<Integer> urne = new ArrayList<>();
    private List<Integer> bestScores;
    private int correctPredictions = 0;

    public BingoServiceImpl() throws RemoteException {
        initializeUrne();
        bestScores = new ArrayList<>();
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
        try {
            BingoServiceImpl bingoService = new BingoServiceImpl();
            Naming.rebind("rmi://localhost:1098/" + id, bingoService);
            System.out.print("Connection done ");	
            return "rmi://localhost:1098/" + id;
        } catch (RemoteException e) {
            e.printStackTrace();
            return "RemoteException: Pb de création d'objet";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "MalformedURLException: Pb de création d'objet";
        }
    }
}
