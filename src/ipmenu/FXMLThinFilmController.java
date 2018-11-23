/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cstuser
 */
public class FXMLThinFilmController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Line topLine;
    @FXML
    private Line bottomLine;
    @FXML
    private AnchorPane pane;
    double y = 100;
    double x = 0;
    double x2 = 200;
    double x3 = 150;
    @FXML
    private ComboBox materialC;

    @FXML
    private Rectangle material;
    @FXML
    private Label phaseL;
    @FXML
    private Label message;
    @FXML
    private TextField indexT;
    double indexF;
    double indexM;
    @FXML

    private Line ray1 = new Line();
    private Line ray2;
    private Line ray3;
    double enhanced = 400;
    double destroyed = 400;

    @FXML
    private ListView lW1;
    @FXML
    private ListView lW2;
    @FXML
    private TextField thicknessT;
    double thickness;
    private ObservableList<String> list1 = FXCollections.observableArrayList();
    private ObservableList<String> list2 = FXCollections.observableArrayList();

    public void helpBAction(ActionEvent ae) {

        Stage helpW = new Stage();

        helpW.setTitle("Help");

        TextArea ta = new TextArea("Please select the material and enter the thickness and "
                + "the index of refraction of the thin film to calculate the enhanced "
                + "and the destroyed wavelengths in nm ");

        ta.setWrapText(true);
        ta.setEditable(false);

        VBox layout = new VBox(20);

        layout.getChildren().addAll(ta);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        helpW.setScene(scene1);

        helpW.showAndWait();

    }

    @FXML
    private void doneButtonAction(ActionEvent event) {
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root1);
            ipmenu.IPMenu.stage.setScene(scene);
            ipmenu.IPMenu.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startBAction(ActionEvent ae) {

        pane.getChildren().remove(ray1);
        pane.getChildren().remove(ray2);
        pane.getChildren().remove(ray3);
        y = 100;
        x = 0;
        x2 = 200;
        x3 = 150;

        list1.clear();
        list2.clear();
        list1.add("Enhanced");
        list2.add("Destroyed");
        lW2.setItems(list2);
        lW1.setItems(list1);
        enhanced = 400;
        destroyed = 400;
        try {
            indexF = Double.parseDouble(indexT.getText());
            thickness = Double.parseDouble(thicknessT.getText()) * 100;
            String selected = materialC.getValue().toString();
            System.out.print(selected);

            if (selected.equals("DIAMOND")) {
                material.setFill(Color.MAGENTA);
                indexM = 2.049;

            } else if (selected.equals("WATER")) {
                material.setFill(Color.BLUE);
                indexM = 1.33;

            } else {
                material.setFill(Color.WHITE);
                indexM = 1.5;
            }

            new AnimationTimer() {
                @Override

                public void handle(long now) {

                    if (x3 <= 400 && x2 <= 400) {
                        drawRays();
                    } else {
                        stop();
                    }
                }
            }.start();

            calculate();
        } catch (Exception e) {

            message.setText("Please enter a numerical value");
        }
    }

    public double getDifractedAngle(double angle, double i) {
        double angle2 = 0;
        angle = Math.toRadians(angle);
        angle2 = Math.acos(Math.cos(angle) / (i));
        angle2 = Math.toDegrees(angle2);
        System.out.println(angle2);
        return angle2;
    }

    public void drawRays() {

        if (x <= 200) {

            pane.getChildren().remove(ray1);
            y = getY(1, x);
            ray1 = new Line(0, 100, x, y);
            ray1.setStroke(Color.FUCHSIA);
            ray1.setStrokeWidth(4);
            x += 10;
            pane.getChildren().add(ray1);
        } else {

            pane.getChildren().remove(ray2);
            y = getY(2, x2);
            ray2 = new Line(200, 300, x2, y);
            ray2.setStroke(Color.FUCHSIA);
            ray2.setStrokeWidth(4);
            x2 += 10;
            pane.getChildren().add(ray2);
        }

        if (x > 150) {

            pane.getChildren().remove(ray3);
            y = getY(3, x3);
            ray3 = new Line(150, 250, x3, y);
            ray3.setStroke(Color.FUCHSIA);
            ray3.setStrokeWidth(4);
            x3 += 10;
            pane.getChildren().add(ray3);

        }
    }

    public void calculate() {

        if (indexF > indexM) {
            phaseL.setText("No phase shift");
            for (int m = 30; (destroyed < 700 || enhanced < 700); m--) {

                enhanced = ((2 * thickness) / (m - 0.5)) * indexF;
                destroyed = ((2 * thickness) / (m)) * indexF;
                if ((destroyed >= 400 && enhanced >= 400) && (destroyed < 700 && enhanced < 700)) {
                    list1.add(enhanced + "");
                    list2.add(destroyed + "");
                }

            }
            lW1.setItems(list1);
            lW2.setItems(list2);
        } else {
            phaseL.setText("Phase shift");
            for (int m = 30; (destroyed < 700 || enhanced < 700); m--) {

                enhanced = ((2 * thickness) / (m)) * indexF;
                destroyed = ((2 * thickness) / (m - 0.5)) * indexF;
                if ((destroyed >= 400 && enhanced >= 400) && (destroyed < 700 && enhanced < 700)) {
                    list1.add(enhanced + "");
                    list2.add(destroyed + "");
                }

            }
            lW1.setItems(list1);
            lW2.setItems(list2);
        }

    }

    public double getY(int ray, double local) {
        double y = 0;

        if (ray == 1) {

            y = local * (300 - 100) / 200 + 100;
        }
        if (ray == 2) {

            y = (local - 200) * -(300 - 100) / 200 + 300;
        }
        if (ray == 3) {

            y = (local - 150) * -(300 - 100) / 200 + 250;
        }

        return y;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materialC.setItems(FXCollections.observableArrayList(
                "DIAMOND", "GLASS", "WATER"));
        list1.add("Enhanced");
        list2.add("Destroyed");
        lW2.setItems(list2);
        lW2.setItems(list1);

        // TODO
    }

}
