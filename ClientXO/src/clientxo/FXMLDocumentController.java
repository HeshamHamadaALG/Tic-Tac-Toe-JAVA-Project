/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxo;

import Network.Client;
import Network.Message;
import static clientxo.ClientXO.client;
import clientxo.playerForm.playTypeController;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author EgyJuba
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button closeBtn, loginBtn, minBtn;

    private double x = 0;
    private double y = 0;
    public  playTypeController ptController;
//    public  GameCoreController crController;
    @FXML
    private void closeButtonAction() {

        // Close Window Button
        Message msg = new Message("CloseConn",new String []{});
        ClientXO.client.sendMessage(msg);
        ClientXO.client.closeConn();
        Stage closeStage = (Stage) closeBtn.getScene().getWindow();
        closeStage.close();
    }

    @FXML
    private void minButtonAction() {

        // Close Window Button
        Stage minStage = (Stage) minBtn.getScene().getWindow();
        minStage.setIconified(true);
    }

    @FXML
    private void SignUpButtonAction(ActionEvent event) throws IOException {
        // Sign up Button
        signUpWindow();
        System.out.println("Sign Up Pressed");
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        // Login Button
        logInWindow();
        System.out.println("Login Pressed");
    }


    /*
 *
Switching Scenes 
*
     */
    // Sign Up Scene 
    public void signUpWindow() {
        Platform.runLater(() -> {
            try {
                Parent SignupView = FXMLLoader.load(getClass().getResource("./signup/signup.fxml"));
                Scene SignupScene = new Scene(SignupView);
                Stage signupwindow = ClientXO.getGlobalStage();

                // to make the stage movable
                SignupView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                SignupView.setOnMouseDragged((MouseEvent e) -> {
                    signupwindow.setX(e.getScreenX() - x);
                    signupwindow.setY(e.getScreenY() - y);
                });

                signupwindow.setScene(SignupScene);
                signupwindow.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
       });
    }

    // Login Scene 
    public void logInWindow() {
        Platform.runLater(() -> {
            try {
                Parent loginView = FXMLLoader.load(getClass().getResource("./login/login.fxml"));
                Scene loginScene = new Scene(loginView);
                Stage loginwindow = ClientXO.getGlobalStage();

                // to make the stage movable
                loginView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                loginView.setOnMouseDragged((MouseEvent e) -> {
                    loginwindow.setX(e.getScreenX() - x);
                    loginwindow.setY(e.getScreenY() - y);
                });

                loginwindow.setScene(loginScene);
                loginwindow.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Levels Scene 
    public void singlePlayWindow() {
        Platform.runLater(() -> {
            try {
                Parent SingleView = FXMLLoader.load(getClass().getResource("./levels/levels.fxml"));
                Scene SingleScene = new Scene(SingleView);
                Stage singlewindow = ClientXO.getGlobalStage();

                // to make the stage movable
                SingleView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                SingleView.setOnMouseDragged((MouseEvent e) -> {
                    singlewindow.setX(e.getScreenX() - x);
                    singlewindow.setY(e.getScreenY() - y);
                });

                singlewindow.setScene(SingleScene);
                singlewindow.show();
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Game Scene 
    public void listWindow() {
        Platform.runLater(() -> {
            try {
                Parent multiView = FXMLLoader.load(getClass().getResource("./list/list.fxml"));
                Scene multiScene = new Scene(multiView);
                Stage multiwindow = ClientXO.getGlobalStage();

                // to make the stage movable
                multiView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                multiView.setOnMouseDragged((MouseEvent e) -> {
                    multiwindow.setX(e.getScreenX() - x);
                    multiwindow.setY(e.getScreenY() - y);
                });

                multiwindow.setScene(multiScene);
                multiwindow.show();
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Levels Scene 
    public void gameWindow() {
        Platform.runLater(() -> {
            try {
                Parent SingleView = FXMLLoader.load(getClass().getResource("./game/GameCore.fxml"));
                Scene SingleScene = new Scene(SingleView);
                Stage singlewindow = ClientXO.getGlobalStage();

                // to make the stage movable
                SingleView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                SingleView.setOnMouseDragged((MouseEvent e) -> {
                    singlewindow.setX(e.getScreenX() - x);
                    singlewindow.setY(e.getScreenY() - y);
                });

                singlewindow.setScene(SingleScene);
                singlewindow.show();
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Type Scene 
    public void playTypeWindow() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./playerForm/playType.fxml"));
                Parent SingleView = loader.load();
                Scene SingleScene = new Scene(SingleView);
                Stage singlewindow = ClientXO.getGlobalStage();
                ptController = (playTypeController) loader.getController();
                // to make the stage movable
                SingleView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                SingleView.setOnMouseDragged((MouseEvent e) -> {
                    singlewindow.setX(e.getScreenX() - x);
                    singlewindow.setY(e.getScreenY() - y);
                });

                singlewindow.setScene(SingleScene);
                singlewindow.show();
            } catch (IOException ex) {
                //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
       
    // Multi Scene
        public void multiGameWindow() {
        Platform.runLater(() -> {
            try {
                System.out.println("LOading MULTIGAME");
                Parent MultiView = FXMLLoader.load(getClass().getResource("./multigame/MultiGame.fxml"));
                Scene MultiScene = new Scene(MultiView);
                Stage Multiwindow = ClientXO.getGlobalStage();

                // to make the stage movable
                MultiView.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });
                MultiView.setOnMouseDragged((MouseEvent e) -> {
                    Multiwindow.setX(e.getScreenX() - x);
                    Multiwindow.setY(e.getScreenY() - y);
                });

                Multiwindow.setScene(MultiScene);
                Multiwindow.show();
            } catch (IOException ex) {
                System.out.println("I can't Load Window");
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }


    // Main Scene 
    public void mainWindow() {
        try {
            Parent MainView = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene mainScene = new Scene(MainView);
            Stage mainwindow = ClientXO.getGlobalStage();

            // to make the stage movable
            MainView.setOnMousePressed((MouseEvent e) -> {
                x = e.getSceneX();
                y = e.getSceneY();
            });
            MainView.setOnMouseDragged((MouseEvent e) -> {
                mainwindow.setX(e.getScreenX() - x);
                mainwindow.setY(e.getScreenY() - y);
            });

            mainwindow.setScene(mainScene);
            mainwindow.show();
        } catch (IOException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void alertLogin(){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.initStyle(StageStyle.UNDECORATED);
           alert.setTitle("Login Alert !!");
           alert.getDialogPane().setStyle("-fx-background-color:#f4f4f4;");
           alert.setHeaderText(null);
           alert.setContentText("You Entered Wronge UserName Or Password  !!");
           alert.showAndWait();
    }
    
    public void winAlert(String state){
                Platform.runLater(() -> {
                    Dialog alert = new Dialog();
                    alert.initStyle(StageStyle.UNDECORATED);
                    
                    ButtonType AgainButton= new ButtonType("Again",ButtonData.OK_DONE);
                    ButtonType CancelButton= new ButtonType("Cancel",ButtonData.OK_DONE);
                    alert.getDialogPane().getButtonTypes().addAll(AgainButton,CancelButton);
                    
                    if(null != state)switch (state) {
                        case "WIN":
                            alert.setTitle("You Won !!");
                            alert.setGraphic(new ImageView(this.getClass().getResource("win.png").toString()));
                            alert.setContentText("You Won YAAAAY  !!");
                            break;
                        case "LOSE":
                            alert.setTitle("You Lose !!");
                            alert.setGraphic(new ImageView(this.getClass().getResource("lose.png").toString()));
                            alert.setContentText("You Lose  :( :( ");
                            break;
                        case "DRAW":
                            alert.setTitle("Game Over !!");
                            alert.setGraphic(new ImageView(this.getClass().getResource("draw.png").toString()));
                            alert.setContentText("No Winners Today ");
                            break;
                        default:
                            break;
                    }
                    
                    alert.getDialogPane().setStyle("-fx-background-color:#D5F200;");
                    alert.setHeaderText(null);
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == AgainButton){
                       new FXMLDocumentController().singlePlayWindow();
                    } else if(result.get() == CancelButton){
                       new FXMLDocumentController().playTypeWindow();
                    }
                }); }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
