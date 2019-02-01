/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.game;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    @FXML 
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
   // private Button Cells[] = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
    @FXML
    int playCount = 2;
    @FXML
    Image xo;

    
@FXML
private void closeButtonAction(){
        
        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();    
}
@FXML
public Image CountPlayer(){
            
        if((playCount % 2)==0){
            xo = new Image(getClass().getResourceAsStream("x.png"));
        } else {
            xo = new Image(getClass().getResourceAsStream("o.png"));
        }
        return xo;
        }

@FXML 
private void Btn1(ActionEvent e){
        btn1 = (Button) e.getSource();
        btn1.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn1.setDisable(true);
        System.out.println("Cell 1 clcicked");

}
@FXML
private void Btn2(ActionEvent e){
        btn2 = (Button) e.getSource();
        btn2.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn2.setDisable(true);
        System.out.println("Cell 2 clcicked");
}
@FXML
private void Btn3(ActionEvent e){
        btn3 = (Button) e.getSource();
        btn3.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn3.setDisable(true);
        System.out.println("Cell 3 clcicked");
}
@FXML
private void Btn4(ActionEvent e){
        btn4 = (Button) e.getSource();
        btn4.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn4.setDisable(true);
        System.out.println("Cell 4 clcicked");
}
@FXML
private void Btn5(ActionEvent e){
        btn5 = (Button) e.getSource();
        btn5.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn5.setDisable(true);
        System.out.println("Cell 5 clcicked");
}
@FXML
private void Btn6(ActionEvent e){
        btn6 = (Button) e.getSource();
        btn6.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn6.setDisable(true);
        System.out.println("Cell 6 clcicked");
}
@FXML
private void Btn7(ActionEvent e){
        btn7 = (Button) e.getSource();
        btn7.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn7.setDisable(true);
        System.out.println("Cell 7 clcicked");
}
@FXML
private void Btn8(ActionEvent e){
        btn8 = (Button) e.getSource();
        btn8.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn8.setDisable(true);
        System.out.println("Cell 8 clcicked");
}
@FXML
private void Btn9(ActionEvent e){
        btn9 = (Button) e.getSource();
        btn9.setGraphic(new ImageView(CountPlayer()));
        playCount++;
        btn9.setDisable(true);
        System.out.println("Cell 9 clcicked");
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
