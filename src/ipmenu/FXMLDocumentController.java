/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author cstuser
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label msgLabel;
    @FXML
    private Button mechanicsButton;
    @FXML
    private Button OWMButton;
    @FXML
    private Button EandMButton;
    @FXML
    private Button exitButton;
    
    @FXML
    public void goMechanics(){
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLMechanicsMenu.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void goOWM(){
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXML_OWM_Menu.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void goEandM(){
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXML_EandM_Menu.fxml"));
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
