/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Game.Player;
import Network.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LapTop MarKet
 */
public class DBManger {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public DBManger() {
        System.out.println("Database Connection" + connect());
    }

    boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/tiktok?"
                            + "user=root&password=");
             System.out.println("====> DataBase Connected <====");  
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }
    }

    public int login(String name, String Password) {
        try {
            statement = connect.createStatement();
            String queryst = new String("select * from players where name = '"+name+"' and password = '"+Password+"' ;");
            resultSet = statement.executeQuery(queryst);
            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }
    
    /**
     *
     * @param player
     * @throws SQLException
     */
    public void update(Player player) throws SQLException{
        try {
            String query = ("update players set points = ? where id = ?;");
            PreparedStatement preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt   (1, player.getPoints());
            preparedStmt.setInt   (2, player.getIdnum());
            preparedStmt.executeUpdate();
        } catch(Exception ex){
            
        }
    }
    
    public ArrayList<Player> loadPlayer (){
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            statement = connect.createStatement();
            String queryst = new String("select id,name,points from players ;");
            resultSet = statement.executeQuery(queryst);
            System.out.println("Loading Table");
            while(resultSet.next()) {
                players.add(new Player(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(DBManger.class.getName()).log(Level.SEVERE, null, ex);
        }
         return players;
    }

 public Connection getConnect() {
        return connect;
    }
    

    public void setConnect(Connection connect) {
        this.connect = connect;
    }
    

    
    public String getGameBoard (Message msg){
        String senarioGame="there isn't paused game";
         try {
            statement = connect.createStatement();
            String queryst = new String("select * from game where x_user = '"+ Integer.parseInt(msg.getData()[1]) +"' and o_user = '"+Integer.parseInt(msg.getData()[2])+"' or  x_user = '"+ Integer.parseInt(msg.getData()[2]) +"' and o_user = '"+Integer.parseInt(msg.getData()[1])+"' ;");
            resultSet = statement.executeQuery(queryst);
            if (resultSet.next()) {
                return resultSet.getString("senario");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return senarioGame;
    }


    
}
