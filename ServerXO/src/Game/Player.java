/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Network.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LapTop MarKet
 */

public class Player extends Thread {

    char mark;
    Player opponent;
    Client client;
    int id;
    String name;
    int points;
    boolean isOnline;

    /**
     * Constructs a handler thread for a given socket and mark initializes the
     * stream fields, displays the first two welcoming messages.
     */
    public Player() {
        this.id = -1;
        this.client=null;
        this.isOnline = false;
    }

    public Player(int ID,String name,int points) {
        this.id = ID;
        this.name= name;
        this.points = points;
        this.client=null;
        this.isOnline = false;
    }

//    public Player(Socket socket, char mark) {
//        this.client.socket = socket;
//        this.mark = mark;
//        try {
//            input = new BufferedReader(
//                    new InputStreamReader(socket.getInputStream()));
//            output = new PrintWriter(socket.getOutputStream(), true);
//            output.println("WELCOME " + mark);
//            output.println("MESSAGE Waiting for opponent to connect");
//        } catch (IOException e) {
//            System.out.println("Player died: " + e);
//        }
//    }

    public void run() {
        try {
            client.output.writeObject(new Message("Login",new String[]{"Accept"}));
            while (true) {
                Message msg = (Message) client.input.readObject();
                System.out.println(msg.getData()[0]);
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//        /**
//         * Accepts notification of who the opponent is.
//         */

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
//

    /**
     * Handles the otherPlayerMoved message.
     */

}
