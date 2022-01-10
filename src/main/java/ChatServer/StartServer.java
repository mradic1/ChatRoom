/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatServer;

/**
 *
 * @author mradi
 */
public class StartServer {

    public static void main(String[] args) {

        System.out.println("Starting chat server");

        Server chatServer = new Server(2000);
        chatServer.start();

    }

}
