/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Tere
 */
public class FXMLCapacitorsController implements Initializable {
    
    //variables
    private boolean start;
    private boolean charge;
    private boolean discharge;
    private double lastFrameTime = 0.0;
    private double currentTime;
    private double Q;//charge
    private double Qm;//charge max
    private double V;//voltage
    private double Vs;//voltage
    private double C; //capacitance 
    private double R; //resistance
    private double I;// current 
    private double Im; //current max
    private AnimationTimer timer ;
    private long initialTime = System.nanoTime();
    public XYChart.Series series = new XYChart.Series();
    private double tempTime;
    
    
    private double xArrow;
    private double yArrow;
    private double dirArrow;
    private double xOfCircuit = 100;
    private double yOfCircuit = 100;
    
    private GraphicsContext gc;

    
    @FXML
    private GridPane grid;
    
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button doneButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button helpButton;
    
    @FXML
    private LineChart chart;
    
    @FXML
    private TextField voltageTF;
    @FXML
    private TextField capacitanceTF;
    @FXML
    private TextField resistanceTF;
    
    @FXML
    private RadioButton chargeRB;
    @FXML
    private RadioButton dischargeRB;
    
    @FXML
    private Label msgLabel;
    
    @FXML
    private void startButtonAction(ActionEvent event) {
        start = true;
        timer.start();
        msgLabel.setText("You can press \"pause\" to pause the simulation. ");
        pauseButton.setDisable(false);
        if (charge = true) {
            dischargeRB.setDisable(true);
        }
        else if(charge = false){
            chargeRB.setDisable(true);
        }
    }
    
    @FXML
    private void doneButtonAction(ActionEvent event) {
        try{
            Parent root1 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root1);
            IPMenu.stage.setScene(scene);
            IPMenu.stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void exitButtonAction(ActionEvent event) {
        
        System.exit(0);
    }
    
    
    @FXML
    private void pauseButtonAction(ActionEvent event) {
        if(start){ 
           start = false;
           timer.stop();
           pauseButton.setDisable(true);
           tempTime = currentTime;
       }
       msgLabel.setText("Press \"Start\" to resume the simulation");
    }
    
    @FXML
    private void helpButtonAction(ActionEvent event) {
        msgLabel.setText("Hello! To use this application chose \"charge\" or \"discharge\" and enter a value for:\nThe voltage of the battery in volts, the capacitance of the capacitor in micro Farads, the resistance of the resistor in ohms"
                + "\nand press Start!\n\"Done\" brings you to the main menu, \"Pause\" pauses the simulation and \"Exit\" quits the whole application\nHave fun!");
        
        
    }
    
    public void charge(){
        charge = true;
    }
    
    public void discharge(){
        discharge = true;
    }
    
   
    
    public void update(double t){
        C = Double.parseDouble(capacitanceTF.getText()) * Math.pow(10, -6);//capacitance of the capacitor, assigned by the user 
        Vs = Double.parseDouble(voltageTF.getText()) ;//voltage of the souce (battery) assogned by the user
        R = Double.parseDouble(resistanceTF.getText()) * Math.pow(10, 5);
        Qm = C*Vs;
        Im= Qm/(R*C);   
        if (charge){
            drawShapes(gc);
            if ((int)t%2 == 0) {
                
                xArrow += 20;
                
            }
            Q = Qm * (1-Math.pow(Math.E, -t/(R*C)))*Math.pow(10, 3);
            V = Vs* (1-Math.pow(Math.E, -t/(R*C)));
            I = Im * (Math.pow(Math.E, -t/(R*C)));
            for (int i = 0; i < 5; i++) {
                drawArrow(gc);
            }
        }
        
        if(discharge){
            drawShapes(gc);
            if ((int)t%2 == 0) {
                
                xArrow += 20;
                
            }
            Q = Qm * (Math.pow(Math.E, -t/(R*C)));
            V = Vs* (Math.pow(Math.E, -t/(R*C)));
            I = Im * (Math.pow(Math.E, -t/(R*C)));
            for (int i = 0; i < 5; i++) {
                drawArrow(gc);
            }
        }

    }
    
    public void plotGraph(double t, XYChart.Series series){
        //XYChart.Series series = new XYChart.Series();
        
        
        ArrayList<Double> times = new ArrayList<Double>();
          if((int)t%5==0){
              times.add(t);
              series.getData().add(new XYChart.Data(times.get(0), Q));
              chart.getData().add(series);
          }
    }
    
    public void newAnim(){
       timer = new AnimationTimer() {
           @Override
            public void handle(long now) {
                currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                if(start){
                   currentTime = tempTime; 
                   currentTime = (now - initialTime) / 1000000000.0 - tempTime;
                    update(currentTime);
                    plotGraph(currentTime, series);
                }
                else if(start == false){
                    stop();
                    
                }
                

            }
       };
   
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        XYChart.Series series = new XYChart.Series();
        Canvas canvas = new Canvas(480,440);
        gc = canvas.getGraphicsContext2D();
        grid.add(canvas, 0, 0);
        msgLabel.setText("Please choose discharge or charge.");

        
        

        timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                
                if(start){
                    currentTime = 0; 
                   currentTime = (now - initialTime) / 1000000000.0;
                    update(currentTime);
                    plotGraph(currentTime, series);
                }

            }
        
        };
    }    
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
     private void drawShapes(GraphicsContext gc) {
        Image img = new Image(fileURL("./assets/whiteBackground.png"));

         if (charge) {
             gc.drawImage(img, 0, 0, 490, 440);
             drawChargingCircuit(gc);
             drawResistor(gc);
         }
         if (discharge) {
             //Image img = new Image(fileURL("./assets/whiteBackground.png"));
            gc.drawImage(img, 0, 0, 490, 440);
            drawDischargingCircuit(gc);
            drawResistor(gc);
         }
         else{
             msgLabel.setText("Please select charge or dischage");
         }
    }
    
    public void drawChargingCircuit(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(3);
        gc.strokeRect(xOfCircuit + 150, yOfCircuit, xOfCircuit + 100, yOfCircuit + 50);
        gc.strokeRect(xOfCircuit, yOfCircuit, xOfCircuit + 50, yOfCircuit + 50);
        drawBattery(gc);
        drawCapacitor(gc);
        xArrow = xOfCircuit + 50;
        yArrow = yOfCircuit;
        //setPosArrow(xOfCircuit + 50, yOfCircuit);
        setDirArrow(-1);
   
        
    }
    
    public void drawDischargingCircuit(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeRect(xOfCircuit + 150, yOfCircuit, xOfCircuit + 100, yOfCircuit + 50);
        drawCapacitor(gc);
        xArrow = xOfCircuit + 300;
        yArrow = yOfCircuit;
        setDirArrow(1);
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeText("-", xOfCircuit + 375, yOfCircuit + 55);
        gc.strokeText("+", xOfCircuit + 375, yOfCircuit + 70);

    }
    
    public void drawResistor(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        double xi = xOfCircuit + 150;
        double yi = yOfCircuit + 45;
        gc.strokeLine(xi, yi, xi = xi+10, yi = yi+10);
        gc.strokeLine(xi, yi, xi = xi-20, yi = yi+10);
        gc.strokeLine(xi, yi, xi = xi+20, yi = yi+10);
        gc.strokeLine(xi, yi, xi = xi-20, yi = yi+10);
        gc.strokeLine(xi, yi, xi = xi+20, yi = yi+10);
        gc.strokeLine(xi, yi, xi = xi-10, yi = yi+10);

    }
    
    public void drawCapacitor(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(xOfCircuit + 325, yOfCircuit + 55, xOfCircuit +365, yOfCircuit + 55);
        gc.strokeLine(xOfCircuit + 325, yOfCircuit + 65, xOfCircuit +365, yOfCircuit + 65);
    }
    
    public void drawBattery(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(xOfCircuit - 20, yOfCircuit + 60, xOfCircuit + 20, yOfCircuit+ 60);
        gc.strokeLine(xOfCircuit -30, yOfCircuit + 70, xOfCircuit + 30, yOfCircuit +70);
    }
    
    public void drawArrow(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.strokeLine(xArrow, yArrow, xArrow+ (dirArrow)*20, yArrow + 20);
        gc.strokeLine(xArrow, yArrow, xArrow+ (dirArrow)*20, yArrow - 20);
        
        
    }

    
    
    public void setDirArrow(double newDir){
        dirArrow = newDir;
    }
    
   
    
}
