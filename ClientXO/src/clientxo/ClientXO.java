/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo;

import Network.Client;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent; 
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author EgyJuba
 */
public class ClientXO extends Application {

    // to make application movable by mouse
    private double xOffset = 0;
    private double yOffset = 0;
    private static Stage globalStage;
    public static Client client;
    public static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        ClientXO.id = id;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);

        // to make the stage movable 
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        globalStage = stage;
        stage.setScene(scene);
        stage.show();
        try {
            // TODO
            client = new Client(new Socket("localhost", 5050));
            client.start();
            System.out.println("Client Connect to Server");
        } catch (IOException ex) {
            System.out.println("Client => No Connect ");
//            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Stage getGlobalStage() {
        return globalStage;
    }

    public static void setGlobalStage(Stage globalStage) {
        ClientXO.globalStage = globalStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
