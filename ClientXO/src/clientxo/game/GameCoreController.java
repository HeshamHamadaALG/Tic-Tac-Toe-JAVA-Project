/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo.game;

import clientxo.FXMLDocumentController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import player.Player;


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
    private TableColumn<Player, Integer> tblId;
    @FXML
    private TableColumn<Player, String> tblNames; 
    @FXML
    private TableColumn<Player, Integer> tblScore;
    @FXML
    private TableView<Player> tableScores;
        
    
    public GameCoreController() {
        this.tableScores = new TableView<>();
        this.tblId = new TableColumn();
        this.tblNames = new TableColumn();
        this.tblScore = new TableColumn();
    }

    
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

@FXML
private void backAction(ActionEvent event) throws IOException{
    new FXMLDocumentController().playTypeWindow();
        System.out.println("Back Pressed");
}
   // The Observable List
    ObservableList<Player> usersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // Database Connection
//        Connection connect = database.getConnect();
//        System.out.println("====> DataBase Connected <====");  
//        try {
//            ResultSet res = connect.createStatement().executeQuery("select * from players ;");
//            while(res.next()){
//                usersList.add(new Player(res.getInt("id"),res.getString("name"),res.getInt("points")));
//                System.out.println("Id : " +  res.getInt("id") + "  Name : "  + res.getString("name") + "  Points : " + res.getInt("points"));
//            }
//                System.out.println("===> table Connected");
//        } catch (SQLException ex) {
//            System.out.println("===>  No table Connection");
//            Logger.getLogger(serverxo.FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        
//        //Initializing the columns 
//        tblId.setCellValueFactory(new PropertyValueFactory<>("idnum"));
//        tblNames.setCellValueFactory(new PropertyValueFactory<>("names"));
//        tblScore.setCellValueFactory(new PropertyValueFactory<>("points"));
//        tableScores.setItems(usersList);
          tableScores.setItems(usersList);
    }       
}
