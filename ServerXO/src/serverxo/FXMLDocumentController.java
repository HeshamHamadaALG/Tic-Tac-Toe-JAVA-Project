/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverxo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author EgyJuba
 */
public class FXMLDocumentController implements Initializable {
    

    @FXML
    Button btn;
    @FXML
    int Count = 2;
    @FXML
    Image onOff;
    @FXML
    String state;
    
    @FXML 
    private Button closeBtn, minBtn;
    
//    @FXML
//    private TableColumn<, String> tblNames, tblScore;
//    @FXML
//    private TableView<> tableScores;

    
@FXML
public Image Count(){
            
        if((Count % 2)==0){
            onOff = new Image(getClass().getResourceAsStream("on.png"));
            state = "on";
        } else {
            onOff = new Image(getClass().getResourceAsStream("off.png"));
            state = "off";
        }
        return onOff;
        }


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
    public void ServerAction(ActionEvent e) {
        btn = (Button) e.getSource();
        btn.setGraphic(new ImageView(Count()));

        Count++;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("off.png"))));
    }    
    
}
