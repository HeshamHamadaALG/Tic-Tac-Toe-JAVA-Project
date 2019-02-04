/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class FXMLDocumentController implements Initializable {
    
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
private void SignUpButtonAction(ActionEvent event) throws IOException{
        // Sign up Button
        signUpWindow();
        System.out.println("Sign Up Pressed");
}




@FXML
private void loginButtonAction(ActionEvent event) throws IOException{
    // Login Button
        logInWindow();
        System.out.println("Login Pressed");
}


/*
 *
Switching Scenes 
*
*/

    // Sign Up Scene 
    public void  signUpWindow(){
        try {
            Parent SignupView = FXMLLoader.load(getClass().getResource("./signup/signup.fxml"));
            Scene SignupScene = new Scene(SignupView);
            Stage signupwindow = ClientXO.getGlobalStage();
            
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
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
}

     // Login Scene 
    public void  logInWindow(){
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("./login/login.fxml"));
            Scene loginScene = new Scene(loginView);
            Stage loginwindow = ClientXO.getGlobalStage();
            
            // to make the stage movable
            loginView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            loginView.setOnMouseDragged((MouseEvent e) -> {
                loginwindow.setX(e.getScreenX() - x);
                loginwindow.setY(e.getScreenY() - y);
            });
            
            loginwindow.setScene(loginScene);
            loginwindow.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    // Levels Scene 
    public void  singlePlayWindow(){
        try {
            Parent SingleView = FXMLLoader.load(getClass().getResource("./levels/levels.fxml"));
            Scene SingleScene = new Scene(SingleView);
            Stage singlewindow = ClientXO.getGlobalStage();
            
            // to make the stage movable
            SingleView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            SingleView.setOnMouseDragged((MouseEvent e) -> {
                singlewindow.setX(e.getScreenX() - x);
                singlewindow.setY(e.getScreenY() - y);
            });
            
            singlewindow.setScene(SingleScene);
            singlewindow.show();
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
     // Game Scene 
    public void  multiPlayWindow(){
        try {
            Parent multiView = FXMLLoader.load(getClass().getResource("./game/GameCore.fxml"));
            Scene multiScene = new Scene(multiView);
            Stage multiwindow = ClientXO.getGlobalStage();
            
            // to make the stage movable
            multiView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            multiView.setOnMouseDragged((MouseEvent e) -> {
                multiwindow.setX(e.getScreenX() - x);
                multiwindow.setY(e.getScreenY() - y);
            });
            
            multiwindow.setScene(multiScene);
            multiwindow.show();
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Levels Scene 
    public void  gameWindow(){
        try {
            Parent SingleView = FXMLLoader.load(getClass().getResource("./game/GameCore.fxml"));
            Scene SingleScene = new Scene(SingleView);
            Stage singlewindow = ClientXO.getGlobalStage();
            
            // to make the stage movable
            SingleView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            SingleView.setOnMouseDragged((MouseEvent e) -> {
                singlewindow.setX(e.getScreenX() - x);
                singlewindow.setY(e.getScreenY() - y);
            });
            
            singlewindow.setScene(SingleScene);
            singlewindow.show();
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    // Type Scene 
    public void  playTypeWindow(){
        try {
            Parent SingleView = FXMLLoader.load(getClass().getResource("./playerForm/playType.fxml"));
            Scene SingleScene = new Scene(SingleView);
            Stage singlewindow = ClientXO.getGlobalStage();
            
            // to make the stage movable
            SingleView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            SingleView.setOnMouseDragged((MouseEvent e) -> {
                singlewindow.setX(e.getScreenX() - x);
                singlewindow.setY(e.getScreenY() - y);
            });
            
            singlewindow.setScene(SingleScene);
            singlewindow.show();
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
    // Main Scene 
    public void  mainWindow(){
        try {
            Parent MainView = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene mainScene = new Scene(MainView);
            Stage mainwindow = ClientXO.getGlobalStage();
            
            // to make the stage movable
            MainView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            MainView.setOnMouseDragged((MouseEvent e) -> {
                mainwindow.setX(e.getScreenX() - x);
                mainwindow.setY(e.getScreenY() - y);
            });
            
            mainwindow.setScene(mainScene);
            mainwindow.show();
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
