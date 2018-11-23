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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import static javafx.application.ConditionalFeature.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
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
public class FXMLDiagramController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label message;

    private boolean startPressed = false;
    private int pressed = -1;

    @FXML
    private ListView lW1;
    @FXML
    private ListView lW2;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField focalD;
    @FXML
    private TextField heightT;
    @FXML
    private TextField objectD;
    static private ImagePattern arrow = null;
    static private ImagePattern arrow2 = null;
    private double lastX22, lastY22, lastX3, lastY3, hi, m, image, object, focal;
    private double x1, x11, x2, x22, x3, x33;
    private double y1, y11, y3, y22 = 0;
    private double ho = 100;
    private Circle focalDot, focalDot2;
    private Line imageLine, ray1, ray2, ray3, ray11, ray22, ray33, objectLine;
    private Label objectLabel = new Label("Object");
    private Label imageLabel = new Label("Image");
    private Rectangle arrowFrame2 = new Rectangle();
    private Rectangle arrowFrame = new Rectangle();
    private ObservableList<String> list1 = FXCollections.observableArrayList(
            Arrays.asList("Image Distance", "Height", "Magnification", "Real or Virtual"));
    private ObservableList<String> list2 = FXCollections.observableArrayList();

    private String data1, data2, data3, data4;

    public void switchBAction(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLdivergent.fxml"));
        Scene scene = new Scene(root);

        scene.getRoot().requestFocus();

        IPMenu.stage.setScene(scene);
        IPMenu.stage.show();

    }

    public void startBAction(ActionEvent ae) {

        list2.clear();

        pane.getChildren().remove(arrowFrame2);
        pane.getChildren().remove(arrowFrame);
        pane.getChildren().remove(objectLabel);
        pane.getChildren().remove(imageLabel);
        pane.getChildren().remove(focalDot);
        pane.getChildren().remove(focalDot2);
        pane.getChildren().remove(objectLine);
        pane.getChildren().remove(imageLine);
        pane.getChildren().remove(ray11);
        pane.getChildren().remove(ray1);
        pane.getChildren().remove(ray2);
        pane.getChildren().remove(ray22);
        pane.getChildren().remove(ray3);
        pane.getChildren().remove(ray33);
        try {
            focal = Double.parseDouble(focalD.getText()) * 5;
            object = Double.parseDouble(objectD.getText()) * 5;
            message.setText("successful diagram");
            ho = Double.parseDouble(heightT.getText());
        
            x1 = 450.0 - object;
            x11 = 450;
            x33 = 450;
            x2 = 450 - object;

            if (object != focal) {

                focalDot = new Circle((450.0 - focal) / 2, 300, 5 / 2, Color.RED);

                focalDot2 = new Circle((450.0 + focal) / 2, 300, 5 / 2, Color.RED);

                objectLine = new Line((450.0 - object) / 2, 300, (450.0 - object) / 2, (600 - ho) / 2);
                setArrows(1);

                pane.getChildren().add(focalDot);
                pane.getChildren().add(focalDot2);
                pane.getChildren().add(objectLine);

                drawImage();

                new AnimationTimer() {
                    @Override

                    public void handle(long now) {

                        //if (pressed == 1) {
                        update();
                        if (!((image < 0 && x33 > 0) || (image > 0 && x33 < 900)) && !((image < 0 && x22 > 0) || (image > 0 && x22 < 900)) && !((image < 0 && x11 > 0 && x1 < 900) || (image > 0 && x11 < 900))) {
                            stop();

                            imageLine = new Line((450 + image) / 2, 300, (450 + image) / 2, (600 - (ho * m)) / 2);
                            imageLine.setStroke(Color.BLUE);
                            setArrows(2);
                            pane.getChildren().add(imageLine);
                            pane.getChildren().add(arrowFrame2);
                            pane.getChildren().add(imageLabel);

                        }

                    }
                }.start();
                pressed *= -1;

            } else {
                message.setText("The image is formed at infinity");
            }
        } catch (Exception e) {
            message.setText("Please enter a numerical value");
        }

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

    public void drawImage() {
        image = (object * focal / (object - focal));
        m = -image / object;
        data1 = image / 5 + "";
        DecimalFormat df = new DecimalFormat("####0.00");
        data2 = ho * m + "";
        data3 = "" + df.format(m);

        if (image > 0) {
            lastX3 = 450 - object;//\\
            lastY3 = 600 - ho;

            lastX22 = 450;
            lastY22 = 600 - ho;
            data4 = "" + " Real";

        } else {
            lastX3 = 450.0 - focal;//\\
            lastY3 = 600;

            lastX22 = 450 + focal;
            lastY22 = 600;
            data4 = "" + " Virtual";
        }

        list2.add(data1);
        list2.add(data2);
        list2.add(data3);
        list2.add(data4);
        lW2.setItems(list2);
        x3 = lastX3;
        x22 = lastX22;

    }

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    public void setArrows(int a) {

        if (a == 1) {
            pane.getChildren().remove(arrowFrame);
            arrowFrame.setX((450.0 - object - 20) / 2);
            arrowFrame.setY((1200 - ho) / 4);
            objectLabel.setLayoutX((450.0 - object - 20) / 2);
            objectLabel.setLayoutY((600 - ho - 20) / 2);
            pane.getChildren().add(arrowFrame);
            pane.getChildren().add(objectLabel);
        } else if (a == 2) {
            pane.getChildren().remove(arrowFrame2);
            arrowFrame2.setX((450 + image - 20) / 2);
            arrowFrame2.setY((1200 - (ho * m)) / 4);
            imageLabel.setLayoutX((450 + image - 20) / 2);

            if (image < 0) {
                imageLabel.setLayoutY((600 - (ho * m) - 20) / 2);
                arrowFrame2.setFill(arrow);
            } else {

                arrowFrame2.setFill(arrow2);

                imageLabel.setLayoutY((600 - (ho * m) + 20) / 2);
            }

        }
    }

    public void update() {

        if ((image < 0 && x11 > 0 && x1 < 900) || (image > 0 && x11 < 900)) {
            y1 = getY(1, x1) + 600;
            pane.getChildren().remove(ray1);
            ray1 = new Line((450 - object) / 2, (600 - ho) / 2, x1 / 2, y1 / 2);
            ray1.setStroke(Color.GREEN);

            pane.getChildren().add(ray1);

            x1 += 10;

            int r = (int) (x2 / 100);
            if (r >= 4) {
                pane.getChildren().remove(ray11);
                y11 = getY(11, x11) + 600;
                ray11 = new Line(450 / 2, 300, x11 / 2, y11 / 2);
                ray11.setStroke(Color.GREEN);
                ray11.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                pane.getChildren().add(ray11);

                if (image < 0) {
                    x11 -= 20;
                } else {
                    x11 += 20;
                }
            }
        }
        if ((image < 0 && x22 > 0) || (image > 0 && x22 < 900)) {
            pane.getChildren().remove(ray2);
            ray2 = new Line((450 - object) / 2, (600 - ho) / 2, x2 / 2, (600 - ho) / 2);
            ray2.setStroke(Color.ORANGE);
            pane.getChildren().add(ray2);

            x2 += 10;

            int r1 = (int) (x2 / 100);
            if (r1 >= 4) {
                pane.getChildren().remove(ray22);
                y22 = getY(22, x22) + 600;
                ray22 = new Line((lastX22) / 2, (lastY22) / 2, x22 / 2, y22 / 2);
                ray22.setStroke(Color.ORANGE);

                ray22.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                pane.getChildren().add(ray22);

                if (image < 0) {
                    x22 -= 20;
                } else {
                    x22 += 20;
                }

            }
        }

        if ((image < 0 && x33 > 0) || (image > 0 && x33 < 900)) {
            y3 = getY(3, x3) + 600;
            pane.getChildren().remove(ray3);
            ray3 = new Line(lastX3 / 2, lastY3 / 2, x3 / 2, y3 / 2);
            ray3.setStroke(Color.PURPLE);
            pane.getChildren().add(ray3);

            x3 += 10;
            int r2 = (int) (x3 / 100);
            if (r2 >= 4) {
                pane.getChildren().remove(ray33);
                ray33 = new Line(450 / 2, (600 - (ho * m)) / 2, x33 / 2, (600 - (ho * m)) / 2);
                ray33.getStrokeDashArray().addAll(10d, 10d, 10d, 10d);
                ray33.setStroke(Color.PURPLE);

                pane.getChildren().add(ray33);
                if (image < 0) {
                    x33 -= 20;
                } else {
                    x33 += 20;
                }
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

            if (image > 0) {

                y = ((local - 450 - focal)) * ((-ho * 1.00 * m + ho) / (image));

            } else {

                y = ((local - 450 - focal)) * ((-ho * 1.00 * m) / (image - focal));

            }
        } else if (ray == 3) {
            if (image > 0) {
                y = (local - 450 + focal) * ((-ho * 1.00 * m + ho) / (object));
            } else {
                y = (local - 450 + focal) * ((-ho * 1.00 * m) / (focal));
            }
        }
        return y;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lW1.setItems(list1);

        arrowFrame2.setLayoutX(0);
        arrowFrame2.setLayoutY(0);
        arrowFrame2.setHeight(20);
        arrowFrame2.setWidth(20);

        long initialTime = System.nanoTime();
        arrowFrame.setLayoutX(0);
        arrowFrame.setLayoutY(0);
        arrowFrame.setHeight(20);
        arrowFrame.setWidth(20);
        arrow = new ImagePattern(new Image(fileURL("./images/arrow.png")));
        arrow2 = new ImagePattern(new Image(fileURL("./images/arrow2.png")));
        arrowFrame.setFill(arrow);

    }

}
