/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.list;

import Network.Message;
import clientxo.ClientXO;
import clientxo.FXMLDocumentController;
//import java.awt.TextArea;
//import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import player.Player;

/**
 *
 * @author EgyJuba
 */
public class ListController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button closeBtn;

    @FXML
    private TableColumn<Player, Integer> tblId;
    @FXML
    private TableColumn<Player, String> tblNames;
    @FXML
    private TableColumn<Player, Integer> tblScore;
    @FXML
    private TableView<Player> tableScores;

    public ListController() {
        this.tableScores = new TableView<>();
        this.tblId = new TableColumn();
        this.tblNames = new TableColumn();
        this.tblScore = new TableColumn();
    }

    @FXML
    private void closeButtonAction() {

        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        new FXMLDocumentController().playTypeWindow();
        System.out.println("Back Pressed");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("You are in List");

        tblId.setCellValueFactory(new PropertyValueFactory<>("idnum"));
        tblNames.setCellValueFactory(new PropertyValueFactory<>("names"));
        tblScore.setCellValueFactory(new PropertyValueFactory<>("points"));
    }
}
