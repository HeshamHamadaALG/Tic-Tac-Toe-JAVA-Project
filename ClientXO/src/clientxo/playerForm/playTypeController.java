/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.playerForm;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author EgyJuba
 */
public class playTypeController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML 
    private Button closeBtn, loginBtn, minBtn;
    
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
private void SingleButtonAction(ActionEvent event) throws IOException{
    // sign up Button
    Parent SignupView = FXMLLoader.load(getClass().getResource("../game/GameCore.fxml"));
    Scene SignupScene = new Scene(SignupView);
    Stage signupwindow = (Stage)((Node)event.getSource()).getScene().getWindow();
    
    // to make the stage movable 
        SignupView.setOnMousePressed((MouseEvent e) -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        SignupView.setOnMouseDragged((MouseEvent e) -> {
            signupwindow.setX(e.getScreenX() - x);
            signupwindow.setY(e.getScreenY() - y);
        });
    
    signupwindow.setScene(SignupScene);
    signupwindow.show();
    System.out.println("sign-up Pressed");
}


@FXML
private void MultiButtonAction(ActionEvent event) throws IOException{
    // Login Butto
    Parent loginView = FXMLLoader.load(getClass().getResource("../game/GameCore.fxml"));
    Scene loginScene = new Scene(loginView);
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    
    // to make the stage movable 
        loginView.setOnMousePressed((MouseEvent e) -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        loginView.setOnMouseDragged((MouseEvent e) -> {
            window.setX(e.getScreenX() - x);
            window.setY(e.getScreenY() - y);
        });
    
    window.setScene(loginScene);
    window.show();
    System.out.println("Login Pressed");
}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
