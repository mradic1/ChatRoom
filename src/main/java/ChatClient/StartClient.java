/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatClient;

import java.net.*;
import java.io.*;

/**
 *
 * @author mradi
 */
public class StartClient {

    public static void main(String[] args) {
        System.out.println("Welcome to chat client\n");

        System.out.println("Enter your name: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String clientName = "";

        try {

            clientName = reader.readLine();

        } catch (IOException ex) {

            System.out.println("Error in reader");
        }

        System.out.println("\n--------------------------");
        System.out.println("| Hello " + clientName);
        System.out.println("--------------------------");

        int newPort = 0;

        try {
            System.out.println("Opening login socket");
            Socket connectionSocket = new Socket("127.0.0.1", 2000);

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            PrintWriter outputStream = new PrintWriter(connectionSocket.getOutputStream(), true);

            System.out.println("Sending handshake message");
            outputStream.println(clientName);

            newPort = Integer.valueOf(inputStream.readLine());
            System.out.println("Received new TCP port: " + String.valueOf(newPort));
            connectionSocket.close();

        } catch (IOException ex) {
            System.out.println("Error in connection");
            System.out.println(ex.getMessage());
        }

        try {

            System.out.println("Opening new port");
            Socket clientSocket = new Socket("127.0.0.1", newPort);

            System.out.println("Creating listener thread");
            Thread listeningThread = new Thread(new ClientListener(clientSocket.getInputStream()));
            listeningThread.start();

            PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {

                BufferedReader messageReader = new BufferedReader(new InputStreamReader(System.in));
                outputStream.println(messageReader.readLine());
            }

        } catch (IOException ex) {

            System.out.println("Error in connection");
            System.out.println(ex.getMessage());
        }

    }
}
