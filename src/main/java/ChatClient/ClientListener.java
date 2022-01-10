/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatClient;

import java.io.*;

/**
 *
 * @author mradi
 */
public class ClientListener implements Runnable {

    InputStream inStream;

    public ClientListener(InputStream inputStream) {
        inStream = inputStream;
    }

    public synchronized void run() {

        System.out.println("Listener thread started");

        BufferedReader messageReader = new BufferedReader(new InputStreamReader(inStream));

        while (true) {
            try {

                System.out.println("Waiting for new message");
                String line = messageReader.readLine();

                if (!line.isEmpty()) {
                    System.out.println("Received new message");
                    System.out.println(line);
                }
            } catch (IOException ex) {
                System.out.println("Error in client listener");
                System.out.println(ex.getMessage());
            }
        }
    }

}
