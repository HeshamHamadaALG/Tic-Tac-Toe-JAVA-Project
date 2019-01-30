/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.game;

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

/**
 *
 * @author EgyJuba
 */
public class GameCoreController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML 
    private Button closeBtn;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    
    private double x = 0; 
    private double y = 0;

@FXML
private void closeButtonAction(){
        
        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();    
}

@FXML
private void loginAction(ActionEvent event) throws IOException{
    // Login Butto
    Parent login = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
    Scene loginScene = new Scene(login);
    Stage loginwindow = (Stage)((Node)event.getSource()).getScene().getWindow();
    
    // to make the stage movable 
        login.setOnMousePressed((MouseEvent e) -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        login.setOnMouseDragged((MouseEvent e) -> {
            loginwindow.setX(e.getScreenX() - x);
            loginwindow.setY(e.getScreenY() - y);
        });
    
    loginwindow.setScene(loginScene);
    loginwindow.show();
    System.out.println("Login");
}

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
