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
public class Server {

    private ServerClient[] connectedClients;
    private int connectionPort;

    private ServerSocket connectionSocket;
    private Socket connectionClient;

    public Server(int connection) {
        connectedClients = new ServerClient[10];
        connectionPort = connection;
    }

    public void start() {

        System.out.println("Chat server started");

        try {

            connectionSocket = new ServerSocket();
            connectionSocket.bind(new InetSocketAddress("127.0.0.1", connectionPort));

        } catch (Exception ex) {
            System.out.println("Error in connection acception");
            System.out.println(ex.getMessage());
        }

        while (true) {

            try {
                System.out.println("Waiting new connection");
                connectionClient = connectionSocket.accept();
                System.out.println("New connection accepted");
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(connectionClient.getInputStream()));
                PrintWriter outputStream = new PrintWriter(connectionClient.getOutputStream(), true);

                this.createConnection(inputStream, outputStream);

                connectionClient.close();

            } catch (Exception ex) {

                System.out.println("Error in creating new client");
                System.out.println(ex.getMessage());

            }
        }
    }

    private void createConnection(BufferedReader in, PrintWriter out) {

        System.out.println("Creating new connection");

        int newPort = 0;

        for (int i = 0; i < 10; i++) {

            if (connectedClients[i] != null) {

                if (connectedClients[i].getClientPort() > newPort) {
                    newPort = connectedClients[i].getClientPort();
                }
            }

        }
        if (newPort == 0) {
            newPort = connectionPort + 1;
        } else {
            newPort++;
        }

        System.out.println("New client port is: " + String.valueOf(newPort));

        try {

            String line = in.readLine();

            ServerClient newClient = new ServerClient(line, newPort, this);

            for (int i = 0; i < 10; i++) {
                if (connectedClients[i] == null) {
                    connectedClients[i] = newClient;
                    System.out.println("New client added to slot " + String.valueOf(i));
                    break;
                }
            }

            System.out.println("Creating new thread");

            Thread newClientThread = new Thread(newClient);
            newClientThread.start();

            System.out.println("New thread started sending port");
            out.println(String.valueOf(newPort));

        } catch (Exception ex) {
            System.out.println("Error in new client connection");
            System.out.println(ex.getMessage());
        }

    }

    public void pushToClients(ChatMessage message) {

        for (int i = 0; i < 10; i++) {

            if (connectedClients[i] != null) {
                connectedClients[i].pushMessage(message);
            }
        }
    }
}
