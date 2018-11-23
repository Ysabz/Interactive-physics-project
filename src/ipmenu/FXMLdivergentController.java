/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yasam
 */
public class FXMLdivergentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane pane;
    @FXML
    private Rectangle lensFrame;
    @FXML
    private Label message;
    @FXML
    private TextField focalD;
    private Label objectLabel = new Label("Object");
    private Label imageLabel = new Label("Image");
    @FXML
    static private ImagePattern lens = null;
    static private ImagePattern arrow = null;
    @FXML
    private TextField objectD;
    @FXML
    private TextField heightT;
    @FXML
    private ListView lW1;
    @FXML
    private ListView lW2;
    @FXML
    private Line horizon;
    private double m, hi, image, object, focal;
    private int pressed = -1;
    private double ho = 170;
    private Circle focalDot = new Circle();
    private Circle focalDot2 = new Circle();
    private Line objectLine = new Line();
    private Line imageLine = new Line();
    private Line ray1, ray2, ray3, ray11, ray22, ray33;
    private double x1, x2, x3, x11, x22, x33;
    private double y1, y11, y2, y22, y3, y33;
    private double lastX22 = 450 + focal;
    private double lastY22 = 450;
    private double lastX3 = 450.0 - focal;
    private double lastY3 = 450;
    private Rectangle arrowFrame = new Rectangle();
    private Rectangle arrowFrame2 = new Rectangle();
    private ObservableList<String> list1 = FXCollections.observableArrayList(
            Arrays.asList("Image Distance", "Height", "Magnification", "Real or Virtual"));
    private ObservableList<String> list2 = FXCollections.observableArrayList();
    private String data1, data2, data3, data4;

    public void switchBAction(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDiagram.fxml"));
        Scene scene = new Scene(root);

        scene.getRoot().requestFocus();

        IPMenu.stage.setScene(scene);
        IPMenu.stage.show();

    }

    @FXML
    private void doneButtonAction(ActionEvent event) {
        try {
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startBAction(ActionEvent ae) {
        list2.clear();
        pane.getChildren().remove(objectLabel);
        pane.getChildren().remove(imageLabel);
        pane.getChildren().remove(focalDot);
        pane.getChildren().remove(focalDot2);
        pane.getChildren().remove(objectLine);
        pane.getChildren().remove(imageLine);
        pane.getChildren().remove(arrowFrame2);
        pane.getChildren().remove(arrowFrame);
        pane.getChildren().remove(ray11);
        pane.getChildren().remove(ray1);
        pane.getChildren().remove(ray2);
        pane.getChildren().remove(ray22);
        pane.getChildren().remove(ray3);
        pane.getChildren().remove(ray33);
        y1 = 0;
        y2 = 0;
        y3 = 0;
        y11 = 0;
        y22 = 0;
        y33 = 0;
        try {
             ho = Double.parseDouble(heightT.getText());
             focal = Double.parseDouble(focalD.getText()) * 5;
            object = Double.parseDouble(objectD.getText()) * 5;
            message.setText("successful diagram");
            x1 = 450.0 - object;
            x11 = 450;
            x33 = 450;
            x2 = 450 - object;
            x3 = 450 - object;
            x22 = 450 - focal;
            System.out.println(focal + " " + object);
            focalDot = new Circle((450.0 - focal) / 2, 300, 5 / 2, Color.RED);

            focalDot2 = new Circle((450.0 + focal) / 2, 300, 5 / 2, Color.RED);

            objectLine = new Line((450.0 - object) / 2, 300, (450.0 - object) / 2, (600 - ho) / 2);
            arrowFrame.setX((450 - object - 20) / 2);
            arrowFrame.setY((1200 - ho) / 4);
            objectLabel.setLayoutX((450.0 - object - 20) / 2);
            objectLabel.setLayoutY((600 - ho - 20) / 2);

            pane.getChildren().add(objectLabel);
            pane.getChildren().add(arrowFrame);
            pane.getChildren().add(focalDot);
            pane.getChildren().add(focalDot2);
            pane.getChildren().add(objectLine);
            drawImage();

            new AnimationTimer() {
                @Override

                public void handle(long now) {

                    if (pressed == 1) {
                        update();

                    } else {

                        stop();

                        pressed *= -1;

                    }
                }
            }.start();
            pressed *= -1;
        } catch (Exception e) {

            message.setText("Please enter a numerical value");
        }
    }

    public void helpBAction(ActionEvent ae) {

        Stage helpW = new Stage();

        helpW.setTitle("Help");

        TextArea ta = new TextArea("Please select the focal distance of the"
                + " lense and the distance of object from the lense to get the"
                + " ray diagram and the fromed image");

        ta.setWrapText(true);
        ta.setEditable(false);
        VBox layout = new VBox(20);

        layout.getChildren().addAll(ta);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 300, 250);

        helpW.setScene(scene1);

        helpW.showAndWait();

    }

    public void update() {

        if (x1 <= 900 && y1 <= 900) {
            y1 = getY(1, x1) + 600;
            pane.getChildren().remove(ray1);
            ray1 = new Line((450 - object) / 2, (600 - ho) / 2, x1 / 2, y1 / 2);
            ray1.setStroke(Color.GREEN);

            pane.getChildren().add(ray1);

            x1 += 10;
        }
        {
            int temp = (int) (x1 / 100);
            if (temp >= 4) {
                pane.getChildren().remove(ray11);
                y11 = getY(11, x11) + 600;
                ray11 = new Line(450 / 2, 600 / 2, x11 / 2, y11 / 2);//(400 - (ho * m));
                ray11.setStroke(Color.GREEN);
                ray11.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                pane.getChildren().add(ray11);
                x11 -= 20;

            }
        }
        if (x2 <= 900 && y2 <= 900) {
            pane.getChildren().remove(ray2);
            y2 = (600 - ho);
            ray2 = new Line((450 - object) / 2, (600 - ho) / 2, x2 / 2, y2 / 2);
            ray2.setStroke(Color.ORANGE);
            pane.getChildren().add(ray2);

            x2 += 10;
        }
        {
            int temp = (int) (x2 / 100);
            if (temp >= 4) {
                pane.getChildren().remove(ray22);
                y22 = getY(22, x22) + 600;
                ray22 = new Line((450 - focal) / 2, 600 / 2, x22 / 2, y22 / 2);
                ray22.setStroke(Color.ORANGE);

                ray22.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                pane.getChildren().add(ray22);
                x22 += 10;

            }
        }

        if (x3 <= 900 && y3 <= 900) {

            pane.getChildren().remove(ray3);

            y3 = getY(3, x3) + 600 - ho;
            ray3 = new Line((450 - object) / 2, (600 - ho) / 2, x3 / 2, y3 / 2);

            ray3.setStroke(Color.PURPLE);
            pane.getChildren().add(ray3);
            x3 += 10;
        }
        {
            int temp = (int) (x3 / 100);
            if (temp >= 4) {
                pane.getChildren().remove(ray33);
                ray33 = new Line(450 / 2, (600 - (ho * m)) / 2, x33 / 2, (600 - (ho * m)) / 2);
                ray33.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                ray33.setStroke(Color.PURPLE);

                pane.getChildren().add(ray33);
                x33 -= 20;

            }

        }

    }

    public double getY(int ray, double local) {
        double y = 0;

        if (ray == 1) {
            y = (local - 450) * (ho * 1.00 / (object));
        } else if (ray == 11) {
            y = (local - 450) * (-ho * 1.00 * m / image);
        } else if (ray == 22) {

            y = ((local - 450 + focal) * -(ho * m) / (focal + image));

        } else if (ray == 3) {

            y = (local - 450 + object) * (ho / (object + focal));
        }
        return y;
    }

    public void drawImage() {
        image = (object * -focal / (object + focal));
        m = -image / object;
        data1 = image / 5 + "";
        data2 = ho * m + "";
        imageLine = new Line((450 + image) / 2, 300, (450 + image) / 2, (600 - (ho * m)) / 2);
        imageLine.setStroke(Color.BLUE);
        System.out.println(image + " " + (ho * m));
        pane.getChildren().add(imageLine);
        arrowFrame2.setX((450 + image - 20) / 2);
        arrowFrame2.setY((1200 - (ho * m)) / 4);
        pane.getChildren().add(arrowFrame2);
        imageLabel.setLayoutX((450 + image - 20) / 2);
        imageLabel.setLayoutY((600 - (ho * m) - 20) / 2);
        pane.getChildren().add(imageLabel);
        DecimalFormat df = new DecimalFormat("####0.00");

        data3 = "" + df.format(m);
        data4 = "" + " Virtual";
        list2.add(data1);
        list2.add(data2);
        list2.add(data3);
        list2.add(data4);
        lW2.setItems(list2);
    }

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arrowFrame.setLayoutX(0);
        arrowFrame.setLayoutY(0);
        arrowFrame.setHeight(20);
        arrowFrame.setWidth(20);
        arrowFrame2.setLayoutX(0);
        arrowFrame2.setLayoutY(0);
        arrowFrame2.setHeight(20);
        arrowFrame2.setWidth(20);
        lW1.setItems(list1);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        lens = new ImagePattern(new Image(fileURL("./images/divergent.png")));
        arrow = new ImagePattern(new Image(fileURL("./images/arrow.png")));
        lensFrame.setFill(lens);
        arrowFrame.setFill(arrow);
        arrowFrame2.setFill(arrow);
        lensFrame.toBack();
        horizon.toBack();

    }

}
