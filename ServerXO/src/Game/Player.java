/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Network.Message;
import SinglePlayer.AI;
import SinglePlayer.EasyAI;
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
            //sara

            //end
            while (true) {
                System.out.println("Listening Player");
                Message msg = (Message) input.readObject();
                //Sara

                System.out.println(msg.getType());
                if (msg.getType().equals("multiPlay")) {
                    handleMultiplayer(msg);
                }
                if(msg.getType().equals("EasySingle"))
                {
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                    player.output.writeObject(new Message("StartEasyGame",new String[]{}));
                    intializeGame(player , new EasyAI());
                    System.out.println("StartEasyGame :"+msg.getData()[0]);
                }
                if(msg.getType().equals("MediumSingle")){
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                    player.output.writeObject(new Message("StartMediumGame", new String[]{}));
                    System.out.println("StartMediumGame : "+msg.getData()[0]);
                }
                if(msg.getType().equals("HardSingle")){
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                    player.output.writeObject(new Message("StartHardGame", new String[]{}));
                    System.out.println("StartHardGame : "+msg.getData()[0]);
                }
                if(msg.getType().startsWith("move")){
                    int col = Integer.parseInt(msg.getType().charAt(5)+"");
                    int row = Integer.parseInt(msg.getType().charAt(7)+"");
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                   
                    if(player.game instanceof SingleGame){
                        SingleGame singleGame = (SingleGame) player.game;
                        if(singleGame.legalMove(row, col, player.mark)){
                            singleGame.Turn();
                        }
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
//        /**
//         * Accepts notification of who the opponent is.
//         */

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
//

    public void handleMultiplayer(Message msg) {

        ArrayList<Player> players = GameController.players;
        int playersSize = players.size();
        Message outputMsg = null;
        Player p1 = null;
        Player p2 = null;
        System.out.println(msg.getData()[0] + " " + msg.getData()[1]);
        for (int i = 0; i < playersSize; i++) {
            if (players.get(i).id == Integer.parseInt(msg.getData()[0])) {
                p1 = players.get(i);
            }
            if (players.get(i).id == Integer.parseInt(msg.getData()[1])) {
                p2 = players.get(i);
            }
        }
        if (p1.isOnline && p2.isOnline) {
            try {
                outputMsg = (new Message("multiPlay", new String[]{Integer.toString(p1.id), Integer.toString(p2.id)}));
                p1.output.writeObject(outputMsg);
                p2.output.writeObject(outputMsg);
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void intializeGame(Player player,AI mode){
         SingleGame game = new SingleGame();
         game.p1= player;
         game.Computer = mode;
         game.turn='X';
         player.game = game;
         player.mark = 'X';
    }
    public Player getPlayer(int id){
         ArrayList<Player> players = GameController.players;
        int playersSize = players.size();
        Player player= null;
        for (int i = 0; i < playersSize; i++) {
            if (players.get(i).id == id) {
                player = players.get(i);
            }
        }
        return player;
    }
    /**
     * Handles the otherPlayerMoved message.
     */
}
