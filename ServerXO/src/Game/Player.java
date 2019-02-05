/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Network.Message;
import java.io.BufferedReader;

import java.io.*;
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
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    Game game;
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
        this.socket = null;
        this.isOnline = false;
    }

    public Player(int ID, String name, int points) {
        this.id = ID;
        this.name = name;
        this.points = points;
        this.socket = null;
        this.isOnline = false;
    }


    public void run() {
        try {
            while (true) {
                Message msg = (Message) input.readObject();
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
