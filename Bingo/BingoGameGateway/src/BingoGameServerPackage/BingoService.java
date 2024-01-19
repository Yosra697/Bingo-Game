package BingoGameServerPackage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BingoService extends Remote {
    
    String connect(String id) throws RemoteException;

    String playBingo(int prediction) throws RemoteException;

    String getBestScore() throws RemoteException;

    void recordScore(int score) throws RemoteException;

    boolean isUrneEmpty() throws RemoteException;
}
