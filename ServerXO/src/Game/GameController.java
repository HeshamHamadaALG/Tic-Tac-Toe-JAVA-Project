/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Database.DBManger;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LapTop MarKet
 */
public class GameController extends Thread {

    static ArrayList<Player> players;
    static DBManger dbManger = null;
    ServerSocket listener;

    public GameController() {
        try {
            dbManger = new DBManger();
            players = dbManger.loadPlayer();
            listener = new ServerSocket(8901);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Listening");
                Socket s = new Socket();
                s = listener.accept();
                System.out.println("Hello");
                System.out.println("New Client Connected");
                Client client = new Client(s,new ObjectInputStream(s.getInputStream()),new ObjectOutputStream(s.getOutputStream()));
                client.start();
            } catch (IOException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
