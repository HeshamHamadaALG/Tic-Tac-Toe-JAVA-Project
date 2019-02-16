/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.playerForm;

import Network.Client;
import Network.Message;
import clientxo.ClientXO;
import static clientxo.ClientXO.client;
import clientxo.FXMLDocumentController;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import player.Player;

/**
 *
 * @author EgyJuba
 */
public class playTypeController implements Initializable {

    @FXML
    private Label label;
    @FXML 
    Player player;
    
    
//    public static Text playerName = new Text();
    

    @FXML
    private Button closeBtn, loginBtn, minBtn;

    private double x = 0;
    private double y = 0;

//public playTypeController(){
//            Platform.runLater(() -> {
//            try {
//                Parent SingleView = FXMLLoader.load(getClass().getResource("./playType.fxml"));
//                Scene SingleScene = new Scene(SingleView);
//                Stage singlewindow = ClientXO.getGlobalStage();
//
//                // to make the stage movable
//                SingleView.setOnMousePressed((MouseEvent e) -> {
//                    x = e.getSceneX();
//                    y = e.getSceneY();
//                });
//                SingleView.setOnMouseDragged((MouseEvent e) -> {
//                    singlewindow.setX(e.getScreenX() - x);
//                    singlewindow.setY(e.getScreenY() - y);
//                });
//
//                singlewindow.setScene(SingleScene);
//                singlewindow.show();
//            } catch (IOException ex) {
//                System.out.println("error");
//                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//}
    @FXML
    private void closeButtonAction() {

        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();
         Message msg = new Message("CloseConn",new String []{});
        ClientXO.client.sendMessage(msg);
        ClientXO.client.closeConn();
    }

    @FXML
    private void minButtonAction() {

        // Close Window Button
        Stage minStage = (Stage) minBtn.getScene().getWindow();
        minStage.setIconified(true);
    }

    @FXML
    private void SingleButtonAction(ActionEvent event) throws IOException {
        new FXMLDocumentController().singlePlayWindow();
        System.out.println("Single Player Pressed");
    }

    @FXML
    private void MultiButtonAction(ActionEvent event) throws IOException {

        //sara 
//         Message msg = new Message("multiPlay",new String []{Integer.toString(ClientXO.getId()),"2"});
//         ClientXO.client.sendMessage(msg);
        new FXMLDocumentController().listWindow();
        Message msg = new Message("listRequest", new String[]{Integer.toString(ClientXO.getId())});
        ClientXO.client.sendMessage(msg);
        
        
        System.out.println("Multi Player Pressed");

        //end
    }

    @FXML
    private void LogOutAction(ActionEvent event) throws IOException {
          Message msg = new Message("CloseConn",new String []{});
        ClientXO.client.sendMessage(msg);
        ClientXO.client.closeConn();
       try {
            // TODO
            client = new Client(new Socket("localhost", 8901));
            client.start();
            System.out.println("Client Connect to Server");
        } catch (IOException ex) {
            System.out.println("Client => No Connect ");
//            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        ClientXO.client.redirectToLogin();
//        new FXMLDocumentController().mainWindow();
        System.out.println("Back Pressed");
    }
        
       


    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }



}
