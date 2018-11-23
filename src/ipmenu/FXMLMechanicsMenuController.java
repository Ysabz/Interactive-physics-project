/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class FXMLMechanicsMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label msgLabel;
    @FXML
    private Button dropBombButton;
    @FXML
    private Button poolButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    
    
    @FXML
    public void goDorpBombButton(){
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void goPool(){
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLGame_1.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void exit(){
        System.exit(0);
    }
    @FXML
    public void goBack(){
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
