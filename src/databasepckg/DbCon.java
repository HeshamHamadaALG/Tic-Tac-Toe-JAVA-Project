/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasepckg;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author atarek
 */
public class DbCon {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    DbCon(){
       System.out.println( connect());
    }
    
    boolean connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/tiktok_project?"
                            + "user=root&password=");
            return true;

        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
            
        }
    }
   
    public boolean addNewGame(Game lastGame){
        
        try{
            statement = connect.createStatement();
            
            String queryst = new String ("insert into game (x_user,o_user,senario) values("+lastGame.userX+",'"+lastGame.userO+"','"+lastGame.gameSenario+"')");
            statement.executeUpdate(queryst);
            return true;
           
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public Game getGame(int userId){
        Game game = new Game();
       
        try{
            statement = connect.createStatement();
            
            String queryst = new String ("select * from game where x_user = "+userId+ " or o_user = "+userId+" ORDER BY  id  DESC  limit 1");
            resultSet = statement.executeQuery(queryst);
            //preparedStatement = connect.prepareStatement(queryst);
            //resultSet = preparedStatement.executeQuery(queryst);
            while(resultSet.next())
            {
                game.id =resultSet.getInt("id");
                game.userX = resultSet.getInt("x_user");
                game.userO = resultSet.getInt("o_user");
                game.gameSenario = resultSet.getString("senario");
               
            }
            return game;
           
        }
        catch(Exception ex){
            ex.printStackTrace(); 
            game = null;
            return game;
        } 
    }
     public static void main(String args[]){
       DbCon db =  new DbCon();
       Game gameTest = new Game();
//       gameTest.userX = 2;
//       gameTest.userO = 1;
//       List senario = new ArrayList();
//       senario.add(0,"x");
//       senario.add(1,"o");
//       senario.add(2,"x");
//       senario.add(3,"o");
//       gameTest.gameSenario = senario.toString();
//       db.addNewGame(gameTest);
       gameTest = db.getGame(2);
       System.out.println(gameTest.id);
       System.out.println(gameTest.userX);
       System.out.println(gameTest.userO);
       System.out.println(gameTest.gameSenario);
    }
    
}
