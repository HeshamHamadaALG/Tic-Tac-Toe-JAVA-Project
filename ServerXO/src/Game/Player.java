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
import java.util.ArrayList;
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
    int idnum;
    String names;
    int points;
    boolean isOnline;

    /**
     * Constructs a handler thread for a given socket and mark initializes the
     * stream fields, displays the first two welcoming messages.
     */
    public Player() {
        this.idnum = -1;
        this.socket = null;
        this.isOnline = false;
    }

    public Player(int ID, String name, int points) {
        this.idnum = ID;
        this.names = name;
        this.points = points;
        this.socket = null;
        this.isOnline = false;
    }

    public void run() {
        try {
            //sara
            ArrayList<Player> players = GameController.players;
            int playersSize = players.size();
            Message outputMsg = null;
            //end

            while (true) {
                System.out.println("Listening Player");
                Message msg = (Message) input.readObject();
                //Sara

                System.out.println(msg.getType());
                if (msg.getType().equals("multiPlay")) {

                    Player p1 = null;
                    Player p2 = null;
                    System.out.println(msg.getData()[0]+" "+msg.getData()[1]);
                    for (int i = 0; i < playersSize; i++) {
                        if (players.get(i).idnum == Integer.parseInt(msg.getData()[0])) {
                            p1 = players.get(i);

                        }
                        if (players.get(i).idnum == Integer.parseInt(msg.getData()[1])) {
                            p2 = players.get(i);
                        }
                    }
                    if (p1.isOnline && p2.isOnline) {
                        outputMsg = (new Message("multiPlay", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum)}));
                        p1.output.writeObject(outputMsg);
                        p2.output.writeObject(outputMsg);
                    }

                }
                //end

            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public char getMark() {
        return mark;
    }

    public void setMark(char mark) {
        this.mark = mark;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getIdnum() {
        return idnum;
    }

    public void setIdnum(int idnum) {
        this.idnum = idnum;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
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
