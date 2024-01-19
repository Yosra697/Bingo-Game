package RPCBingoGameServerPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ServeurRPC {

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(8889)) {
			System.out.println("Le serveur est en cours d'exécution...");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				 new Thread(new BingoServiceImplRPC(clientSocket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}