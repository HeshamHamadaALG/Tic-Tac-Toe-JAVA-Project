/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.levels;

import Network.Message;
import clientxo.FXMLDocumentController;
import clientxo.ClientXO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import player.Player;

/**
 *
 * @author EgyJuba
 */
public class levelsController implements Initializable {
        
    @FXML
    private Label label;
    
    @FXML 
    private Button easy;
//    @FXML
//    private Button med;
//    @FXML
//    private Button hard;
    @FXML
    private Button closeBtn, minBtn;
    
    
    private double x = 0; 
    private double y = 0;

    
@FXML
private void closeButtonAction(){
        
        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();    
}

@FXML
private void minButtonAction(){
        
        // Close Window Button
        Stage minStage = (Stage) minBtn.getScene().getWindow();
           minStage.setIconified(true);
}

@FXML
private void easyAction(ActionEvent event) throws IOException{
        ClientXO.client.sendMessage(new Message("EasySingle",new String[]{Integer.toString(ClientXO.getId())})); 
//    new FXMLDocumentController().gameWindow();
//        System.out.println("Easy Level Pressed");
}

@FXML
private void medAction(ActionEvent event) throws IOException{
     ClientXO.client.sendMessage(new Message("MediumSingle",new String[]{Integer.toString(ClientXO.getId())})); 
}

@FXML
private void hardAction(ActionEvent event) throws IOException{
    ClientXO.client.sendMessage(new Message("HardSingle",new String[]{Integer.toString(ClientXO.getId())})); 
}


@FXML
private void backAction(ActionEvent event) throws IOException{
    new FXMLDocumentController().playTypeWindow();
        System.out.println("Back Pressed");
}


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
