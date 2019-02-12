/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Game.Player;
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
                    .getConnection("jdbc:mysql://localhost/tictactoe?"
                            + "user=root&password=");
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
    
    public ArrayList<Player> loadPlayer (){
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            statement = connect.createStatement();
            String queryst = new String("select id,name,points from players ;");
            resultSet = statement.executeQuery(queryst);
            while(resultSet.next()) {
                players.add(new Player(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(DBManger.class.getName()).log(Level.SEVERE, null, ex);
        }
         return players;
    }

 
}
