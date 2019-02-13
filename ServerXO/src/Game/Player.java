/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Network.Message;
import SinglePlayer.AI;
import SinglePlayer.EasyAI;
import SinglePlayer.HardAI;
import SinglePlayer.MediumAI;
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
            while (true) {
                System.out.println("Listening Player");
                Message msg = (Message) input.readObject();
                if (msg == null) {
                    System.out.println("player is offline");
                    return;
                }
                Player p1 = null;
                Player p2 = null;
                Message outputMsg = null;
                //Sara
                System.out.println(msg.getType());
                if (msg.getType().equals("multiPlay")) {
                    System.out.println(msg.getData()[0] + " " + msg.getData()[1]);
                    p1 = getPlayer(Integer.parseInt(msg.getData()[0]));
                    p2 = getPlayer(Integer.parseInt(msg.getData()[1]));
                    if (p2.isOnline) {
                        Message playRequest = (new Message("playRequest", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum)}));
                        p2.output.writeObject(playRequest);
                    }
                } else if (msg.getType().equals("playRequest")) {
                    if (msg.getData()[0].equals("accept")) {
                        p1 = getPlayer(Integer.parseInt(msg.getData()[1]));
                        p2 = getPlayer(Integer.parseInt(msg.getData()[2]));
                        if (p1.isOnline && p2.isOnline) {
                            outputMsg = (new Message("play", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum)}));
                            p1.output.writeObject(outputMsg);
                            p2.output.writeObject(outputMsg);
                            intializeMultiGame(p1, p2);
                        }
                    } else {
                        Message rejected = (new Message("reject", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum)}));
                        p1.output.writeObject(rejected);
                    }
                }
                if (msg.getType().equals("EasySingle")) {
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                    player.output.writeObject(new Message("StartEasyGame", new String[]{}));
                    intializeSingleGame(player, new EasyAI());
                    System.out.println("StartEasyGame :" + msg.getData()[0]);
                }
                if (msg.getType().equals("MediumSingle")) {
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                    player.output.writeObject(new Message("StartMediumGame", new String[]{}));
                    intializeSingleGame(player, new MediumAI());
                    System.out.println("StartMediumGame : " + msg.getData()[0]);
                }
                if (msg.getType().equals("HardSingle")) {
                    Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                    player.output.writeObject(new Message("StartHardGame", new String[]{}));
                    intializeSingleGame(player, new HardAI());
                    System.out.println("StartHardGame : " + msg.getData()[0]);
                }
                if (msg.getType().startsWith("Move")) {
                    handleMove(msg);
                }

                //end
            }
        } catch (IOException ex) {
            System.out.println("client is offline");
            return;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleMultiplayer(Message msg) {
        ArrayList<Player> players = GameController.players;
        int playersSize = players.size();
        Message outputMsg = null;
        Player p1 = null;
        Player p2 = null;
        System.out.println(msg.getData()[0] + " " + msg.getData()[1]);
        for (int i = 0; i < playersSize; i++) {
            if (players.get(i).idnum == Integer.parseInt(msg.getData()[0])) {
                p1 = players.get(i);
            }
            if (players.get(i).idnum == Integer.parseInt(msg.getData()[1])) {
                p2 = players.get(i);
            }
        }
        if (p1.isOnline && p2.isOnline) {
            try {
                outputMsg = (new Message("multiPlay", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum)}));
                p1.output.writeObject(outputMsg);
                p2.output.writeObject(outputMsg);
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void intializeSingleGame(Player player, AI mode) {
        SingleGame game = new SingleGame();
        game.p1 = player;
        game.Computer = mode;
        game.turn = 'X';
        player.game = game;
        player.mark = 'X';
    }

    public void intializeMultiGame(Player p1, Player p2) {
        MultiGame game = new MultiGame();
        game.p1 = p1;
        game.p2 = p2;
        game.turn = 'X';
        p1.game = game;
        p1.mark = 'X';
        p2.game = game;
        p2.mark = 'O';
    }

    public void handleMove(Message msg) {
        System.out.println("request to " + msg.getType());
        int col = Integer.parseInt(msg.getType().charAt(5) + "");
        int row = Integer.parseInt(msg.getType().charAt(7) + "");
        Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
        if (player.game instanceof SingleGame) {
            handleSingleGame(player, col, row);
        } else {
            handleMultiGame(player, col, row);
        }
    }

    public void handleSingleGame(Player player, int col, int row) {
        try {
            SingleGame singleGame = (SingleGame) player.game;
            if (singleGame.legalMove(col, row, player.mark)) {
                player.output.writeObject(new Message("Move X " + col + " " + row, new String[]{}));
                game.noOfTurns++;
                if (singleGame.hasWinner()) {
                    Thread.sleep(500);
                    player.output.writeObject(new Message("WIN", new String[]{}));
                } else if (singleGame.boardFilledUp()) {
                    Thread.sleep(500);
                    player.output.writeObject(new Message("DRAW", new String[]{}));
                } else {
                    int move[] = singleGame.computerTurn();
                    game.noOfTurns++;
                    singleGame.legalMove(move[0], move[1], 'O');
                    player.output.writeObject(new Message("Move O " + move[0] + " " + move[1], new String[]{}));
                    System.out.println("Computer Mover " + move[0] + " " + move[1]);
                    if (singleGame.hasWinner()) {
                        Thread.sleep(500);
                        player.output.writeObject(new Message("LOSE", new String[]{}));
                    } else if (singleGame.boardFilledUp()) {
                        Thread.sleep(500);
                        player.output.writeObject(new Message("DRAW", new String[]{}));
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleMultiGame(Player player, int col, int row) {
        try {
            MultiGame multiGame = (MultiGame) player.game;
            Player opponent = (player == multiGame.p1) ? multiGame.p2 : multiGame.p1;
            if (multiGame.legalMove(col, row, player.mark)) {
                player.output.writeObject(new Message("Move " + player.mark + " " + col + " " + row, new String[]{}));
                opponent.output.writeObject(new Message("Move " + player.mark + " " + col + " " + row, new String[]{}));
                game.noOfTurns++;
                if (multiGame.hasWinner()) {
                    Thread.sleep(500);
                    player.output.writeObject(new Message("WIN", new String[]{}));
                    opponent.output.writeObject(new Message("LOSE", new String[]{}));

                } else if (multiGame.boardFilledUp()) {
                    Thread.sleep(500);
                    player.output.writeObject(new Message("DRAW", new String[]{}));
                    opponent.output.writeObject(new Message("DRAW", new String[]{}));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Player getPlayer(int id) {
        ArrayList<Player> players = GameController.players;
        int playersSize = players.size();
        Player player = null;
        for (int i = 0; i < playersSize; i++) {
            if (players.get(i).idnum == id) {
                player = players.get(i);
            }
        }
        return player;
    }

    /**
     * Handles the otherPlayerMoved message.
     */
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
}
