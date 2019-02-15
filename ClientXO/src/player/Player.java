package player;

import javafx.scene.text.Text;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author EgyJuba
 */
public class Player {
    int idnum;
    String names;
    int points;
    boolean isOnline;
    Text online;


     public Player() {
        this.idnum = -1;
        this.isOnline = false;
    }

    public Player(int ID, String name, int points) {
        this.idnum = ID;
        this.names = name;
        this.points = points;
        this.isOnline = false;
    }

    public int getIdnum() {
        return idnum;
    }

    public void setIdnum(int idnum) {
        this.idnum = idnum;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Text getOnline() {
        if(isOnline == true){
              online = new Text("Online");
              online.setStyle("-fx-fill: green; -fx-font-weight: bold;");
        } else if(isOnline == false){
              online = new Text("Offline");
              online.setStyle("-fx-fill: red; -fx-font-weight: bold;");        }
        return online;
    }

    public void setOnline(Text online) {
        this.online = online;
    }

}
