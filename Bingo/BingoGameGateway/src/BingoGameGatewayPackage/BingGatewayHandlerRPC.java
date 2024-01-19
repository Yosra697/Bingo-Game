package BingoGameGatewayPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.Naming;

import BingoGameServerPackage.BingoService;

public class BingGatewayHandlerRPC implements Runnable {
	private final Socket clientSocket;
	public BingGatewayHandlerRPC(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	@Override
	public void run() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			Socket rpcSocket = new Socket("localhost", 8889);
			ObjectOutputStream rpcOut = new ObjectOutputStream(rpcSocket.getOutputStream());
			ObjectInputStream rpcIn = new ObjectInputStream(rpcSocket.getInputStream());
			String x = reader.readLine();
			 if ("Connect".equals(x)) {
	                String objectId = reader.readLine();}
			while (true) {
				x = reader.readLine();
				try {
					if (x.contains("Option 2 ")) {
						System.out.println("bessst");
						rpcOut.writeObject("bestScore");
						writer.println(rpcIn.readObject());
					} else if (x.contains("Option 3 ")) {
						break;
					} else {
						rpcOut.writeObject("Round");
						int choix = Integer.parseInt(x);
						System.out.println(choix);
						rpcOut.writeObject(choix);
						writer.println(rpcIn.readObject());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}}catch(IOException e) {
			e.printStackTrace();
		}
		}}
		


