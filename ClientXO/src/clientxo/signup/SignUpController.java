/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.signup;

import Network.Message;
import clientxo.ClientXO;
import clientxo.FXMLDocumentController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import player.Player;
//import player.Player;

/**
 *
 * @author EgyJuba
 */
public class SignUpController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button closeBtn, minBtn;
    @FXML
    public Label passerror, repasserror;
    @FXML
    public  TextField name, email;
    @FXML
    public PasswordField password, repassword;

    Player player;

    private double x = 0;
    private double y = 0;

    @FXML
    private void closeButtonAction() {

        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();
    }

    @FXML
    private void minButtonAction() {

        // Close Window Button
        Stage minStage = (Stage) minBtn.getScene().getWindow();
        minStage.setIconified(true);
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        // Login Button
        new FXMLDocumentController().logInWindow();
        System.out.println("Login Pressed");
    }
    
    
    // Email validation
    public boolean valEmail(String email){
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        return matcher.find();
    }

    @FXML
    private void signAction() throws IOException {
        // Sign up Button
        new FXMLDocumentController().signUpWindow();
        System.out.println("Sign Up Pressed");
        
        // We Will Handle Sign Up Validation

        if (!password.getText().equals(repassword.getText())) {
            System.out.println("*Passwords aren't matching");
            new FXMLDocumentController().alertSignUp();
            password.setText("");
            repassword.setText("");
        } else if(!valEmail(email.getText())){
            new FXMLDocumentController().alertEmail();
            System.out.println("Plz Enter Valid Email");
        } else {
            Message msg = new Message("Signup",new String []{name.getText(), password.getText(), email.getText()});
            ClientXO.client.sendMessage(msg);
            System.out.println("Signup Pressed and msg sent");

        }

    }
    
    @FXML
private void backAction(ActionEvent event) throws IOException{
    new FXMLDocumentController().mainWindow();
        System.out.println("Back Pressed");
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
