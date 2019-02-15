package Game;

import Database.DBManger;
import Network.Message;
import SinglePlayer.AI;
import SinglePlayer.EasyAI;
import SinglePlayer.HardAI;
import SinglePlayer.MediumAI;
import java.io.BufferedReader;

import java.io.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Text;

/**
 * ok
 *
 * @author LapTop MarKet
 */
//public class Player extends Thread {
public class Player {

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
    Text online;


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

    public void startThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        System.out.println("Listening Player");
                        Message msg = (Message) input.readObject();
                        if (msg == null) {
                            Player.this.isOnline = false;
                            if (!Player.this.socket.isClosed()) {
//                                Player.this.input.close();
//                                Player.this.output.close();
                                Player.this.socket.close();
                            }
                            broadCastPlayerList();
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
                                    String senario = GameController.dbManger.getGameBoard(msg);
                                    outputMsg = (new Message("play", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum), senario}));
                                    p1.output.writeObject(outputMsg);
                                    p2.output.writeObject(outputMsg);
                                    intializeMultiGame(p1, p2);
                                }
                            } else {
                                Message rejected = (new Message("reject", new String[]{Integer.toString(p1.idnum), Integer.toString(p2.idnum)}));
                                p1.output.writeObject(rejected);
                            }
                        } else if (msg.getType().equals("chatting")) {
                            Player player = null;
                            player = getPlayer(Integer.parseInt(msg.getData()[0]));

                            MultiGame multiGame = (MultiGame) player.game;
                            Player opponent = (player == multiGame.p1) ? multiGame.p2 : multiGame.p1;
                            //p2 = getPlayer(Integer.parseInt(msg.getData()[1]));
                            Message chat = new Message("chatting", new String[]{msg.getData()[0], msg.getData()[1], player.getNames(), opponent.getNames()});
                            player.output.writeObject(chat);
                            opponent.output.writeObject(chat);
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
                        if (msg.getType().equals("listRequest")) {
                            Player player = getPlayer(Integer.parseInt(msg.getData()[0]));
                            outputMsg = new Message("listResponse", new String[]{});
                            outputMsg.setData(playerListToArray(GameController.players));
                            player.output.writeObject(outputMsg);
                        }
                        //end

                    }
                } catch (IOException ex) {
                    try {
                        System.out.println("Player is offline catch");
                        broadCastPlayerList();
                        Player.this.isOnline = false;
                        if (!Player.this.socket.isClosed()) {
//                            Player.this.input.close();
//                            Player.this.output.close();
                            Player.this.socket.close();
                        }
                        return;
                    } catch (IOException ex1) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

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

    public void broadCastPlayerList() {
        ArrayList<Player> players = GameController.players;
        int listSize = players.size();
        for (int i = 0; i < listSize; i++) {
            Player p = players.get(i);
            if (p.isOnline && p.socket != null) {
                Message msg = new Message("listResponse", new String[]{});
                msg.setData(playerListToArray(GameController.players));
                try {
                    p.output.writeObject(msg);
                } catch (IOException ex) {
                    System.out.println("Can't Send The List");
                }
            }
        }

    }

    public void handleSingleGame(Player player, int col, int row) {
        try {
            SingleGame singleGame = (SingleGame) player.game;
            if (singleGame.legalMove(col, row, player.mark)) {
                player.output.writeObject(new Message("Move X " + col + " " + row, new String[]{}));
                game.noOfTurns++;
                if (singleGame.hasWinner()) {
                    //Add Winning Points
                    Thread.sleep(500);
                    player.output.writeObject(new Message("WIN", new String[]{}));
                    if (singleGame.Computer instanceof EasyAI) {
                        player.setPoints(points += 5);
                    }
                    if (singleGame.Computer instanceof MediumAI) {
                        player.setPoints(points += 10);
                    }
                    if (singleGame.Computer instanceof HardAI) {
                        player.setPoints(points += 15);
                    }
                    GameController.dbManger.update(player);

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
        } catch (SQLException ex) {
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
                    if (multiGame instanceof MultiGame) {
                        Thread.sleep(500);
                        player.output.writeObject(new Message("WIN", new String[]{}));
                        opponent.output.writeObject(new Message("LOSE", new String[]{}));
                        player.setPoints(points += 10);
                        GameController.dbManger.update(player);
                    }

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
        } catch (SQLException ex) {
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

    public String[] playerListToArray(ArrayList<Player> playerList) {
        int listSize = playerList.size();
        String[] result = new String[listSize];
        for (int i = 0; i < listSize; i++) {
            Player p = playerList.get(i);
            result[i] = p.getIdnum() + "/" + p.getNames() + "/" + p.getPoints() + "/" + p.isIsOnline();
            System.out.println("Player 1 :" + result[i]);
        }
        return result;
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
    
    public Text getOnline() {
        if(isOnline == true){
              online = new Text("Online");
              online.setStyle("-fx-fill: green; -fx-font-weight: bold;");
        } else if(isOnline == false){
              online = new Text("Offline");
              online.setStyle("-fx-fill: red; -fx-font-weight: bold;");        }
        return online;
    }

    public void setOnline(Text online) {
        this.online = online;
    }
//
}
