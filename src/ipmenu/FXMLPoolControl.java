
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ipmenu;


import java.net.URL;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.Random;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;

/**
 *Program Animation 1 : PoolBall (Mechanics 1)
 *  Hit A PoolBall on a table.
 *      Ball is affected by a variable of force and Friction decided by the user
 *      Possible bouncing off walls at angle.
 *      Possible trajectory line drawn.
 *  
 * @author Tyler Bentley
 */
public class FXMLPoolControl implements Initializable {
    
    //TODO
    //Clear Graph on reset
    
    
    @FXML
    private Button startButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button resetButton;
    @FXML 
    private Button pauseButton;
    @FXML
    private Label helpMessage;
    @FXML
    private Label buttonInfo;
    @FXML
    private TextField frictionTF;
    @FXML
    private TextField forceTF;
    @FXML
    private TextField angleTF;
    
 
    
    @FXML 
    private LineChart<Number , Number> lineChart;
    //User Input Variables + Values for Formula
    private XYChart.Series series = new XYChart.Series();
    
    public static double force;
    public static double friction;
    public static double angle;
    public static double forceFriction;
    public static double acceleration ;
    public static double deltaVelocity ;
    public static final double DEFAULT_FORCE = 25.0; //Change based on testing *Not Done*
    public static final double DEFAULT_FRICTION = 0.2;
    public static final double DEFAULT_ANGLE = 0;
    public static final double MASS_BALL = 1.70 ;
    public static final double FORCE_OF_GRAVITY = 9.8 ;
    public static final double forceNormal = FORCE_OF_GRAVITY * MASS_BALL ;
   
    
    
    //Variables for AnimationTimer
    private long now;
    public boolean timeRunning = false;
    private long initialTime = System.nanoTime();
    private double lastFrameTime = 0.0;
    public AnimationTimer timer ;
    
    private boolean helped = false;
    private ArrayList<GameObjectPool> circleList ;
    
    double velocity;
   
    // add to pane shortcut
    public void addToPane(Node node) {
        pane.getChildren().add(node);
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
    private void helpButton(ActionEvent e){
        
        if (!helped) {
            helped = true;
            buttonInfo.setOpacity(1);
            helpMessage.setOpacity(1);
        }
        else{
            helped = false ;
            buttonInfo.setOpacity(0);
            helpMessage.setOpacity(0);
        }
    }
    //When Start is Pressed , Set Values from User Input and Start Animation
    @FXML
    private void startButton(ActionEvent e) {
        
        try {
            //System.out.println(forceTF.getText());
            double value = Double.parseDouble(forceTF.getText());
            //System.out.println("test");
            if( value >= 5.0 && value <= 100.0){ force = value; }
            else{force = DEFAULT_FORCE;}
        }
        catch(NumberFormatException ex){force = DEFAULT_FORCE;}
        //Try values input by user + limits , if not a double or out of limit then default option.
        try { 
            
            double value = Double.parseDouble(frictionTF.getText());
           
            if( value >= 0 && value <= 1){ friction = value;}
            else{friction = DEFAULT_FRICTION;}
        }
        catch(NumberFormatException ex){friction = DEFAULT_FRICTION;}
        try { 
            
            double value = Double.parseDouble(angleTF.getText());
           
            if( value >= 0 && value <= 360){ angle = value;}
            else{angle = DEFAULT_ANGLE;}
        }
        catch(NumberFormatException ex){angle = DEFAULT_ANGLE;}
        
        forceFriction =  friction * forceNormal;
        acceleration = forceFriction / MASS_BALL ;
        double velX = force * Math.cos(Math.toRadians(angle));
        double velY = force * Math.sin(Math.toRadians(angle));
        velocity = Math.sqrt(Math.pow(velX,2)+ Math.pow(velY, 2));
        initialTime = System.nanoTime();
        circleList.get(0).getVelocity().setX(velX);
        circleList.get(0).getVelocity().setY(velY);
        circleList.get(0).setAngle(angle);
        timeRunning = true;
        timer.start();
        pauseButton.setDisable(false);
        resetButton.setDisable(false);
        startButton.setDisable(true);
    }
    //stops animation to enter new values them enter
    @FXML
    private void resetButton (ActionEvent e){
       lineChart.getData().clear();
       series = new XYChart.Series();
       initialTime = System.nanoTime();
       startButton.setDisable(false);
       pauseButton.setDisable(true);
       circleList.get(0).setPosition(new Vector2D(100, 220));
       timer.stop();
    }
    //stop animation and call new one without reseting values
    @FXML
    private void pauseButton (ActionEvent ex) 
    {
        if(timeRunning) {
            timeRunning = false;
            pauseButton.setText("Resume");
            resetButton.setDisable(true);
        }
        else {
            timeRunning = true;
            pauseButton.setText("Pause");
            startAnim();
            timer.start();
            resetButton.setDisable(false);
        }
    }
    //creates new animationtimer
    public void startAnim() {
       timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (timeRunning) {
                    
                    double currentTime = (now - initialTime) / 1000000000.0;
                    double  frameDeltaTime = currentTime - lastFrameTime;
                    lastFrameTime = currentTime;
                    
                    // change accel on click only once // Change to ( bombDropped )
                    if(velocity <= 0.08 && velocity != 0.0 ){
                        timer.stop();
                    }
                                
                    //Hit Wall then invert velocity vertical
                    if (circleList.get(0).getPosition().getX() >= 420 ||circleList.get(0).getPosition().getX() <= 30  ) {
                        double temp = circleList.get(0).getAngle();
                        temp = 180 - temp ;
                        circleList.get(0).setAngle(temp);
                        vectorDecomposition(velocity);
                    }
                     //Hit Wall then invert velocity horizontal
                    if (circleList.get(0).getPosition().getY() >= 400 ||circleList.get(0).getPosition().getY() <= 45  ){
                        double temp = circleList.get(0).getAngle();
                        temp = 360 - temp ;
                        circleList.get(0).setAngle(temp);
                        vectorDecomposition(velocity);
                        //System.out.println("Hit New Angle : " + circleList.get(0).getAngle());
                    }  
                    if (frameDeltaTime < 0.20) {
                        circleList.forEach((obj) -> {
                            obj.update(frameDeltaTime);
                        }); 
                        deltaVelocity = acceleration * frameDeltaTime ;
                        velocity += - deltaVelocity ;
                        vectorDecomposition(velocity);;
                    plotChart(currentTime , series);
                    }
                    
                    
                }
                else {
              
                 stop(); 
               
                } 
            } 
        };
       timer.start();
    }
    //called when the game is finished.
    public void finish(){
        timeRunning = false;
        timer.stop();
    }
    public int tempT = -99;
    public void plotChart(double t , XYChart.Series series){
        if ((int) t == tempT ) {}
        else if ((int)t%1 ==0)  {
               series.getData().add(new XYChart.Data((int)t,velocity));
                lineChart.getData().add(series);
                tempT = (int) t; 
        }
    }
    //takes the velocity and outputs X and Y Components for the 2D Vector
    private void vectorDecomposition (double value){
        double angleT = circleList.get(0).getAngle();
        circleList.get(0).getVelocity().setX(velocity * Math.cos(Math.toRadians(angleT)));
        circleList.get(0).getVelocity().setY(velocity * Math.sin(Math.toRadians(angleT)));     
    }
    //start of the programs
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Random rand = new Random();
        lastFrameTime = 0.0f;
        circleList = new ArrayList<>();
        AssetManagerPool.preloadAllAssets();
        pane.setBackground(new Background(AssetManagerPool.getBackground()));
        //create objects
        GameObjectPool cueBall = new GameObjectPool(new Vector2D(100, 220),new Vector2D(0, 0), new Vector2D(0.0, 0.0), 10, 0);
        cueBall.getCircle().setFill(AssetManagerPool.getcueBallImage());
        //add to list to be updated
       circleList.add(cueBall);
        //add to the visible game
        addToPane(cueBall.getCircle());
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                 if (timeRunning) {
                   // Time calculation 
                    double currentTime = (now - initialTime) / 1000000000.0;
                    double  frameDeltaTime = currentTime - lastFrameTime;
                    lastFrameTime = currentTime;
                    
                    
                    if(velocity <= 0.08 && velocity != 0.0 ){
                        
                        timer.stop();
                       
                    }
                    //Hit Wall then invert velocity vertical
                    if (circleList.get(0).getPosition().getX() >= 420 ||circleList.get(0).getPosition().getX() <= 30  ) {
                        double temp = circleList.get(0).getAngle();
                        temp = 180 - temp ;
                        circleList.get(0).setAngle(temp);
                        vectorDecomposition(velocity);
                    }
                     //Hit Wall then invert velocity horizontal
                    if (circleList.get(0).getPosition().getY() >= 400 ||circleList.get(0).getPosition().getY() <= 45  ){
                        double temp = circleList.get(0).getAngle();
                        temp = 360 - temp ;
                        circleList.get(0).setAngle(temp);
                        vectorDecomposition(velocity);
                        
                    }  
                     
                    // Update existing circles (position)
                    if (frameDeltaTime < 0.20) {
                        circleList.forEach((obj) -> {
                            obj.update(frameDeltaTime); 
                        }); 
                        deltaVelocity = acceleration * frameDeltaTime ;
                        velocity += - deltaVelocity ;
                        vectorDecomposition(velocity);
                        
                        plotChart(currentTime , series);
                    } 
                }
                else {
                    stop();
                    
                }
            }
        };
    }
}
