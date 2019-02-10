/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import clientxo.ClientXO;
import clientxo.FXMLDocumentController;
import clientxo.playerForm.playTypeController;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
                System.out.println(msg.getType());
                if (msg.getType().equals("Login")) {
                    System.out.println(msg.getData()[0]);
                    isLogged = handleLogin(msg);
                }
                //sara
                else if (msg.getType().equals("playRequest")){
                     Message message= (new Message("playRequest", new String[]{"accept",msg.getData()[0], msg.getData()[1]}));
                     ClientXO.client.sendMessage(message);
                    //playRequest();
                }
                else if (msg.getType().equals("play")) {
                        multiPlay();
                } else if (msg.getType().equals("StartEasyGame")) {
                    new FXMLDocumentController().gameWindow();;
                }else if (msg.getType().equals("StartMediumGame")) {
                    new FXMLDocumentController().gameWindow();;
                }else if (msg.getType().equals("StartHardGame")) {
                    new FXMLDocumentController().gameWindow();;
                }
                else if (msg.getType().startsWith("Move")) {
                    handleMove(msg.getType());
                }
                else if (msg.getType().startsWith("WIN")) {
                    System.out.println("CONGRATS, YOU WIN");
                     new FXMLDocumentController().singlePlayWindow();
                }
                else if (msg.getType().startsWith("LOSE")) {
                    System.out.println("YOU LOSE");
                     new FXMLDocumentController().singlePlayWindow();
                }
                else if (msg.getType().equals("DRAW")) {
                    System.out.println("DRAW");
                     new FXMLDocumentController().singlePlayWindow();
                }

                //end
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
        new FXMLDocumentController().gameWindow();
    }
    
    public void playRequest(){
        // show pop up to ask user if he wants to play, if he click OK, the client will send a message of type playRequest, and accept data
        
       
    }
}
