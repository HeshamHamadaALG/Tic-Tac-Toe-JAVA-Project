/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import clientxo.ClientXO;
import clientxo.FXMLDocumentController;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author LapTop MarKet
 */
public class Client extends Thread {

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    
    public Client(Socket socket, ObjectInputStream input, ObjectOutputStream output) {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

    }

    public void sendMessage(Message msg) {
        try {
            output.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        Message msg = null;
        boolean isLogged = false;
        //sara change !isLogged to true
        while (true) {
            try {
                msg = (Message) input.readObject();
                if (msg.getType().equals("Login")) {
                    System.out.println(msg.getData()[0]);
                    isLogged = handleLogin(msg);
                }
                //sara
                else if (msg.getType().equals("multiPlay")) {
                    multiPlay();
                     System.out.println(msg.getType());
                }
                else if( msg.getType().equals("Hello"))
                    System.out.println("Hello");
                //end
                //recieve the mesg and redirect 
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    }

    public boolean handleLogin(Message msg) {
        if (msg.getData()[0].equals("Accept")) {
            ClientXO.setId(Integer.parseInt(msg.getData()[1]));
            new FXMLDocumentController().playTypeWindow();
            return true;
        }
            Platform.runLater(() -> 
            new FXMLDocumentController().alertLogin());
        return false;
    }

    public void multiPlay (){
      new FXMLDocumentController().gameWindow();
    }
    
}
