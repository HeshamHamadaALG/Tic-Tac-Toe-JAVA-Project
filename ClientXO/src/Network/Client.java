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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import player.Player;

/**
 *
 * @author LapTop MarKet
 */
public class Client extends Thread {

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    FXMLDocumentController gameController = null;

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

    public void closeConn() {
        try {
            this.input.close();
            this.socket.close();
            this.output.close();
        } catch (IOException ex) {
            System.out.println("can't close the session");

        }
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
                if (msg == null) {
                    System.out.println("server is offline");
                    return;
                }
                System.out.println(msg.getType());
                if (msg.getType().equals("Login")) {
                    System.out.println(msg.getData()[0]);
                    isLogged = handleLogin(msg);
                } else if (msg.getType().equals("Signup")) {
                    System.out.println(msg.getData()[0]);
                    handleSignup(msg);
                } //sara
                else if (msg.getType().equals("playRequest")) {
                    new FXMLDocumentController().playAccept(msg.getData()[0], msg.getData()[1]);

                    //playRequest();
                } else if (msg.getType().equals("play")) {
                    System.out.println(msg.getData()[2]);
                    new FXMLDocumentController().multiGameWindow();
                    if (!msg.getData()[2].equals("")) {
                        retrieveOldGame(msg.getData()[2]);
                    }
                } else if (msg.getType().equals("chatting")) {
                    handleChatting(msg.getData());
                } else if (msg.getType().equals("StartEasyGame")) {
                    new FXMLDocumentController().gameWindow();;
                } else if (msg.getType().equals("StartMediumGame")) {
                    new FXMLDocumentController().gameWindow();;
                } else if (msg.getType().equals("StartHardGame")) {
                    new FXMLDocumentController().gameWindow();;
                } else if (msg.getType().startsWith("Move")) {
                    handleMove(msg.getType());
                } else if (msg.getType().startsWith("WIN")) {
                    System.out.println("CONGRATS, YOU WIN");
                    new FXMLDocumentController().winAlert("WIN");
                } else if (msg.getType().startsWith("LOSE")) {
                    System.out.println("YOU LOSE");
                    new FXMLDocumentController().winAlert("LOSE");
                } else if (msg.getType().equals("DRAW")) {
                    System.out.println("DRAW");
                    new FXMLDocumentController().winAlert("DRAW");
                } else if (msg.getType().equals("listResponse")) {
                    System.out.println("ListPlayerReply");
                    fillPlayerList(msg.getData());
                }
                else if(msg.getType().equals("OpponentLeft")){
                    new FXMLDocumentController().noConnect();
                    System.out.println("OpponentLeft");
                }

                //end
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("server is offline");
                return;
//                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean handleLogin(Message msg) {
        if (msg.getData()[0].equals("Accept")) {
            ClientXO.setId(Integer.parseInt(msg.getData()[1]));
            new FXMLDocumentController().playTypeWindow();
            return true;
        }
        Platform.runLater(()
                -> new FXMLDocumentController().alertLogin());
        return false;
    }

    public void handleMove(String move) {
        System.out.println(move);
        Platform.runLater(() -> {
            char T = move.charAt(5);
            ImageView Turn = new ImageView(new Image(getClass().getResourceAsStream(T + ".png")));
            int col = Integer.parseInt(move.charAt(7) + "");
            int row = Integer.parseInt(move.charAt(9) + "");
            String btnId = "";
            if (col == 0 && row == 0) {
                btnId = "1";
            } else if (col == 1 && row == 0) {
                btnId = "2";
            } else if (col == 2 && row == 0) {
                btnId = "3";
            } else if (col == 0 && row == 1) {
                btnId = "4";
            } else if (col == 1 && row == 1) {
                btnId = "5";
            } else if (col == 2 && row == 1) {
                btnId = "6";
            } else if (col == 0 && row == 2) {
                btnId = "7";
            } else if (col == 1 && row == 2) {
                btnId = "8";
            } else if (col == 2 && row == 2) {
                btnId = "9";
            }
            Button btn = (Button) ClientXO.getGlobalStage().getScene().lookup("#btn" + btnId);
            btn.setGraphic(Turn);
//               gameController.crController.turn(turn,col,row);
        });

    }

    public void multiPlay() {
        new FXMLDocumentController().listWindow();
        System.out.println("You choose MultiGame");
    }

    public void fillPlayerList(String[] players) {
        Platform.runLater(() -> {
            ObservableList<Player> playerList = ArrayToPlayerList(players);
            TableView<Player> playerTable = (TableView<Player>) ClientXO.getGlobalStage().getScene().lookup("#tableScores");
            if (playerTable != null) {
                System.out.println("FILL THE TABLE");
                playerTable.setItems(playerList);
            }
        });
    }

    //unimport stringTokenizier
    public ObservableList<Player> ArrayToPlayerList(String[] players) {
        ObservableList<Player> playerList = FXCollections.observableArrayList();
        System.out.println("ARRTOPLAYERLIST");
        for (int i = 0; i < players.length; i++) {
            StringTokenizer st = new StringTokenizer(players[i], "/");
            Player p = new Player();
            p.setIdnum(Integer.parseInt(st.nextToken()));
            p.setNames(st.nextToken());
            p.setPoints(Integer.parseInt(st.nextToken()));
            p.setIsOnline(Integer.valueOf(st.nextToken()));
            playerList.add(p);
            System.out.println(p.getIdnum() + " " + p.getNames() + " " + p.getPoints() + " " + p.isIsOnline());
        }
        return playerList;
    }

    public void retrieveOldGame(String scenario) {
        Platform.runLater(() -> {
            int ind=0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    char T = scenario.charAt(ind++);
                    if(T=='X'||T=='O'){
                        ImageView Turn = new ImageView(new Image(getClass().getResourceAsStream(T + ".png")));
                        int col = j;
                        int row = i;
                        String btnId = "";
                        if (col == 0 && row == 0) {
                            btnId = "1";
                        } else if (col == 1 && row == 0) {
                            btnId = "2";
                        } else if (col == 2 && row == 0) {
                            btnId = "3";
                        } else if (col == 0 && row == 1) {
                            btnId = "4";
                        } else if (col == 1 && row == 1) {
                            btnId = "5";
                        } else if (col == 2 && row == 1) {
                            btnId = "6";
                        } else if (col == 0 && row == 2) {
                            btnId = "7";
                        } else if (col == 1 && row == 2) {
                            btnId = "8";
                        } else if (col == 2 && row == 2) {
                            btnId = "9";
                        }
                        Button btn = (Button) ClientXO.getGlobalStage().getScene().lookup("#btn" + btnId);
                        btn.setGraphic(Turn);
                    }
                }
            }
        });
    }

    ;
    public void handleChatting(String msg[]) {
        Platform.runLater(() -> {

            TextArea chatArea = (TextArea) ClientXO.getGlobalStage().getScene().lookup("#chatArea");
            if (chatArea != null) {
                chatArea.setText(chatArea.getText() + msg[2] + ": " + msg[1]);
                System.out.println(msg[2] + ": " + msg[1]);
            }
        });
    }

    public void playRequest() {
        // show pop up to ask user if he wants to play, if he click OK, the client will send a message of type playRequest, and accept data

    }

    public void redirectToLogin() {
        new FXMLDocumentController().logInWindow();
    }

    public boolean handleSignup(Message msg) {
        if (msg.getData()[0].equals("Accept")) {
            redirectToLogin();
            return true;
        } else {
            Platform.runLater(()
                    -> new FXMLDocumentController().alertSignUpUsername());
            return false;
        }
    }
}
