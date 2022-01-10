/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatServer;

import java.net.*;
import java.io.*;

/**
 *
 * @author mradi
 */
public class ServerClient implements Runnable {

    private Server mainServer;
    private String clientName;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int listeningPort;
    private PrintWriter outputStream;

    public ServerClient(String name, int port, Server server) {
        clientName = name;
        listeningPort = port;
        mainServer = server;
    }

    public void run() {

        System.out.println("------------------------");
        System.out.println("Starting new client");
        System.out.println("Client Name: " + clientName);
        System.out.println("Client Port: " + String.valueOf(listeningPort));
        System.out.println("------------------------");

        try {

            System.out.println("Creating new communiation socket");
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1", listeningPort));

            clientSocket = serverSocket.accept();

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputStream = new PrintWriter(clientSocket.getOutputStream());

            String line;

            while (true) {

                line = inputStream.readLine();

                if (!line.isEmpty()) {
                    System.out.println("Received new message: " + line);
                    mainServer.pushToClients(new ChatMessage(clientName, line));
                }
            }
        } catch (Exception ex) {

            System.out.println("Error in server client");
            System.out.println(ex.getMessage());
        }
    }

    public int getClientPort() {
        return listeningPort;
    }

    public void pushMessage(ChatMessage message) {
        System.out.println("Pushing message to clients: " + message.clientName + ":  " + message.clientMessage);
        outputStream.println(message.clientName + ":  " + message.clientMessage);
    }

}
