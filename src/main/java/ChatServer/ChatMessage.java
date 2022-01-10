/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatServer;

/**
 *
 * @author mradi
 */
public class ChatMessage {

    public String clientName;
    public String clientMessage;

    public ChatMessage(String name, String message) {
        clientName = name;
        clientMessage = message;
    }
}
