package RPCBingoGameServerPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BingoService extends Remote {
    
    /**
     * Connect a player to the game.
     */
    String connect(String id) throws RemoteException;

    /**
     * Play Bingo by making a prediction.
     */
    String playBingo(int prediction) throws RemoteException;

    /**
     * Get the best recorded score.
     */
    String getBestScore() throws RemoteException;

    /**
     * Record the score of a player.
     */
    void recordScore(int score) throws RemoteException;

    /**
     * Check if the urn is empty.
     */
    boolean isUrneEmpty() throws RemoteException;
}
