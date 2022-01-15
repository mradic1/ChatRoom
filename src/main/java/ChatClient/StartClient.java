/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatClient;

import java.net.*;
import java.io.*;

/**
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

            PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader messageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String message = "";
            String input = "";

            while (true) {
                try {
                    
                    if (messageReader.ready()) {
                        message = messageReader.readLine();
                        System.out.println("Received new message");
                        System.out.println("-".repeat(message.length() + 4));
                        System.out.println(message);
                        System.out.println("-".repeat(message.length() + 4));
                    }

                    if (consoleReader.ready()) {
                        input = consoleReader.readLine();
                        System.out.println("Sending new message");
                        outputStream.println(input);
                        System.out.println("You: " + input);
                    }
                } catch (IOException ex) {
                    System.out.println("Error in client listener");
                    System.out.println(ex.getMessage());
                }
            }

        } catch (IOException ex) {

            System.out.println("Error in connection");
            System.out.println(ex.getMessage());
        }

    }
}
