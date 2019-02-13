/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverxo;

import Database.DBManger;
import Game.GameController;
import Game.Player;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    Button funBic;

    @FXML
    private Button closeBtn, minBtn;

    @FXML
    private TableColumn<Player, Integer> tblId;
    @FXML
    private TableColumn<Player, String> tblNames;
    @FXML
    private TableColumn<Player, Integer> tblScore;
    @FXML
    private TableView<Player> tableScores;
    @FXML
    Image imgPlace;
    DBManger db = new DBManger();
    GameController gc;

    ObservableList<Player> usersList = FXCollections.observableArrayList();

    public FXMLDocumentController() {
        this.tableScores = new TableView<>();
        this.tblId = new TableColumn();
        this.tblNames = new TableColumn();
        this.tblScore = new TableColumn();
    }

    @FXML
    public Image Count() throws IOException {

        if ((Count % 2) == 0) {
            onOff = new Image(getClass().getResourceAsStream("on.png"));
            state = "on";
            imgPlace = new Image(getClass().getResourceAsStream("/image/running.png"));
            // start server
            gc = new GameController();
            gc.start();
            loadUserList();
            // Start Table
            tableScores.setItems(usersList);
        } else {
            onOff = new Image(getClass().getResourceAsStream("off.png"));
            state = "off";
            imgPlace = new Image(getClass().getResourceAsStream("/image/sleep.png"));
            // stop server
            gc.close();

            // Stop Table
            tableScores.setItems(null);
        }
        return onOff;
    }

    @FXML
    private void closeButtonAction() throws IOException {

        // Close Window Button
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        // stop server
        gc.close();
        closeStage.close();
    }

    @FXML
    private void minButtonAction() {

        // Close Window Button
        Stage minStage = (Stage) minBtn.getScene().getWindow();
        minStage.setIconified(true);
    }

    @FXML
    public void ServerAction(ActionEvent e) throws IOException {
        btn = (Button) e.getSource();
        btn.setGraphic(new ImageView(Count()));
        funBic.setGraphic(new ImageView(imgPlace));
        Count++;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("off.png"))));
        // Funn Server Pics
        funBic.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/sleep.png"))));
//         ArrayList<String> playerList =new ArrayList<String>();
//        
//        // Database Connection
//        Connection con = db.getConnect();
//        System.out.println("====> DataBase Connected <====");  
//        try {
//            ResultSet rs = con.createStatement().executeQuery("select * from players ;");
//            while(rs.next()){
//                usersList.add(new Player(rs.getInt("id"),rs.getString("name"),rs.getInt("points")));
//                System.out.println("Id : " +  rs.getInt("id") + "  Name : "  + rs.getString("name") + "  Points : " + rs.getInt("points"));
//                playerList.add(rs.getInt("id") + " "  + rs.getString("name") + " " + rs.getInt("points"));
//            }
//                System.out.println("===> table Connected");
//        } catch (SQLException ex) {
//            System.out.println("===>  No table Connection");
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //Initializing the columns 
        tblId.setCellValueFactory(new PropertyValueFactory<>("idnum"));
        tblNames.setCellValueFactory(new PropertyValueFactory<>("names"));
        tblScore.setCellValueFactory(new PropertyValueFactory<>("points"));

    }
    public String[] playerListToArray( ArrayList<Player> playerList){
        int listSize = playerList.size();
        String[] result = new String[listSize];
        for(int i=0;i<listSize;i++){
            Player p = playerList.get(i);
            result[i]= p.getIdnum()+"/"+p.getNames()+"/"+p.getPoints()+"/"+p.isIsOnline();
            System.out.println("Player 1 :"+result[i]);
        }
        return result;
    }
    
    //unimport stringTokenizier
    public void  ArrayToPlayerList(String [] players){
        ArrayList<Player> playerList = new ArrayList<Player>();
        System.out.println("ARRTOPLAYERLIST");
        for(int i=0;i<players.length;i++){
            StringTokenizer st = new StringTokenizer(players[i],"/");  
            Player p = new Player();
            p.setIdnum(Integer.parseInt(st.nextToken()));
            p.setNames(st.nextToken());
            p.setPoints(Integer.parseInt(st.nextToken()));
            p.setIsOnline(Boolean.valueOf(st.nextToken()));
            playerList.add(p);
            System.out.println(p.getIdnum()+ " " + p.getNames()+" "+p.getPoints()+" "+p.isIsOnline());  
        }
    }
    public void loadUserList(){
        ArrayList<Player> playerList = GameController.players;
        int listSize = playerList.size();
        for (int i = 0; i < listSize; i++) {
            Player p = playerList.get(i);
            usersList.add(p);
            System.out.println("Id : " + p.getIdnum() + "  Name : "  + p.getNames()+ "  Points : " + p.getPoints());
        }
        String [] playerArr = playerListToArray(playerList);
        ArrayToPlayerList(playerArr);
    }

}
