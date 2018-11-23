
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ipmenu;


import ipmenu.GameObjectBomb;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *Program Animation 1 : BombDrop (Mechanics 1)
 *  Dropping a bomb in 2D kinematics and trying to reach the target.
 *  Uses FreeFall Formulae to Calculate and Graph Trajectory of Bomb and Ideal Time/Location of Launch.
 * @author Tyler Bentley
 */
public class FXMLGameControl implements Initializable {
    
    //TODO
    //FIX CHART METHOD
    //FINISH SECOND PROGRAM
    
    
    @FXML
    private TextField speedTF;
    @FXML
    private TextField gravityTF;
    @FXML
    private Button startButton;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button resetButton;
    @FXML 
    private Button pauseButton;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label scoreValue;
    @FXML
    private Label helpMessage;
    @FXML
    private Label buttonInfo;
    
    
    @FXML
    LineChart<Number , Number> lineChart ;
    @FXML
    private CategoryAxis totalTime; 
    @FXML
    private NumberAxis height;
    
    private XYChart.Series series = new XYChart.Series();
    public double timeElapsed = 0;
    public int timeInterval= 0;
    
    public static double velocity;
    public static double gravity;
    public static final double DEFAULT_VELOCITY = 25.0;
    public static final double DEFAULT_GRAVITY = 9.8;
    
    private AnimationTimer animTimer;
    private long now;
    
    private boolean helped = false;
    
    private long initialTime = System.nanoTime();
    private double lastFrameTime = 0.0;
    public boolean timeRunning = true;
    boolean bombDropped = false;
    private ArrayList<GameObjectBomb> circleList ;
    
    //final values used to set limits and math calculations
    private final int MAX_BOMB = 1;
    private final int MAX_SCORE = 500;
    private final int FALL_DISTANCE = 340;
   
    private static double distanceFromBest = 0;
    private static double timeOfFlight = 0;
    //automaticaly drops at perfect time
    private static int AIlaunchCoding ;
    
    private double distance;
    public int score;
    
    private Vector2D BtempPos;
    private Vector2D BtempVel;
    private Vector2D PtempPos;
    private final Vector2D pInitPos = new Vector2D(30 , 60);
    private static Vector2D pInitVel = new Vector2D(velocity , 0);
    private final Vector2D bInitPos = new Vector2D(30 ,80);
    private static Vector2D bInitVel = new Vector2D(velocity , 0);
    
    public AnimationTimer timer ;
    
    
    // add to pane shortcut
    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }
    //increase bomb count to pass max therefore no further change to the acceleration
    @FXML
    private void startButton(ActionEvent e) {
        //Try values input by user + limits , if not a double or out of limit then default option.
        try {
            double value = Double.parseDouble(speedTF.getText().toString());
            if( value >= 20.0 && value <= 100.0){ velocity = value; }
            else{velocity = DEFAULT_VELOCITY;}
        }
        catch(NumberFormatException ex){velocity = DEFAULT_VELOCITY;}
        //Try values input by user + limits , if not a double or out of limit then default option.
        try { 

            double value = Double.parseDouble(gravityTF.getText().toString());

            if( value >= 1.0 && value <= 20.0){ gravity = value; }
            else{gravity = DEFAULT_GRAVITY;}
        }
        catch(NumberFormatException ex){gravity = DEFAULT_GRAVITY;}
        //display values & update values.
        circleList.get(0).setVelocity(new Vector2D(velocity , 0));
        circleList.get(2).setVelocity(new Vector2D(velocity , 0));

        
        timeRunning = true;
        timer.start();
        timeOfFlight = Math.sqrt((2*FALL_DISTANCE / gravity));
        distanceFromBest = circleList.get(1).getPosition().getX()- velocity * timeOfFlight;
        AIlaunchCoding = (int) distanceFromBest;

        
        
        pauseButton.setDisable(false);
        resetButton.setDisable(false);
        startButton.setDisable(true);
    }
    //change the number of bombs for detection in the animation.(allows only one bomb)
    @FXML
    private void helpButton(ActionEvent e){
        if(helped){
            helped = false;
            helpMessage.setOpacity(0);
            buttonInfo.setOpacity(0);
        }
        else{
            helped = true;
            helpMessage.setOpacity(1);
            buttonInfo.setOpacity(1);
        }
    }
    
    //resets animation for new values
    @FXML
    private void resetButton (ActionEvent e){
       startButton.setDisable(false);
       pauseButton.setDisable(true);
       
       lineChart.getData().clear();
       series = new XYChart.Series();
       initialTime = System.nanoTime();
       
       circleList.get(0).setPosition(pInitPos);
       circleList.get(2).setPosition(bInitPos);
       circleList.get(2).setVelocity(new Vector2D(velocity , 0));
       circleList.get(2).setAcceleration(new Vector2D(0, 0));
       
       bombDropped = false;
       timer.stop();
    }
    //working - pause position on click 1 , click 2 resume new anim with old data
    @FXML
    private void pauseButton (ActionEvent ex) 
    {
        if(timeRunning) {
            timeRunning = false;
            pauseButton.setText("Resume");
            resetButton.setDisable(true);
           System.out.println(circleList.get(2).getPosition().getY());
        }
        else {
            timeRunning = true;
            pauseButton.setText("Pause");
            startAnim();
            timer.start();
            resetButton.setDisable(false);
        }
    }
    //Duplicate Series ERROR 
    int tempT = -999;
    public void plotChart(double t , XYChart.Series series){
        
        double height = 380 - circleList.get(2).getPosition().getY();
         
        if ((int) t == tempT ) {}
        else if ((int)t%1 ==0) 
        {
           
            series.getData().add(new XYChart.Data(Integer.toString((int)t),height));
            lineChart.getData().add(series);
            tempT = (int) t;
        }
    }
            
    public void startAnim() {
        
        
        circleList.get(0).setPosition(PtempPos);
        circleList.get(2).setPosition(BtempPos);
        circleList.get(2).setVelocity(BtempVel);
        
        
        
       timer = new AnimationTimer() {
           
            
            @Override
            public void handle(long now) {
                if (timeRunning) {
                    
                    double currentTime = (now - initialTime) / 1000000000.0;
                    double  frameDeltaTime = currentTime - lastFrameTime;
                    lastFrameTime = currentTime;
                    
                    if(circleList.get(2).getPosition().getY() > circleList.get(1).getPosition().getY()) {
                        finish();
                    }
                    
                    
                    // change accel on click only once // Change to ( bombDropped )
                    if(circleList.get(0).getPosition().getX() > AIlaunchCoding ) {
                        if(circleList.get(2).getAcceleration().getY() == 0){
                            circleList.get(2).setAcceleration(new Vector2D(0, 9.8));
                        }
                    }
                    
                    if (frameDeltaTime < 0.20) {
                        circleList.forEach((obj) -> {
                            obj.update(frameDeltaTime);
                        }); 
                        plotChart(currentTime, series);
                    }
                   
                                        
                }
                else {
                
                BtempPos = circleList.get(2).getPosition();
                BtempVel = circleList.get(2).getVelocity();
                PtempPos = circleList.get(0).getPosition();
                
                 stop(); 
                
                } 
            } 
        };
       timer.start();
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
    //called when the game is finished ,need more accurate score if time
    public void finish(){
        timeRunning = false;
       
        if (distance < circleList.get(2).getCircle().getRadius() + circleList.get(1).getCircle().getRadius()) {

           score = MAX_SCORE + 50;
        }
        else {
            int dist =(int) distance;
            score = MAX_SCORE - dist ;
        }
        pane.getChildren().remove(circleList.get(2));
        scoreLabel.setOpacity(Double.MAX_VALUE);
        String tempValue = Integer.toString(score);
        scoreValue.setText(tempValue);
        scoreValue.setOpacity(Double.MAX_VALUE);
        timer.stop();
    }
    //start of the programs
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Random rand = new Random();
        lastFrameTime = 0.0f;
        circleList = new ArrayList<>();
        AssetManagerBomb.preloadAllAssets();
        
        //create objects
        int targetPosition = rand.nextInt(170)+230 ;
        GameObjectBomb plane = new GameObjectBomb(pInitPos,new Vector2D(velocity , 0), new Vector2D(0.0, 0.0), 60);
        GameObjectBomb target = new GameObjectBomb(new Vector2D(targetPosition, 400), new Vector2D(0, 0), new Vector2D(0, 0), 20);
        GameObjectBomb bomb = new Bomb(bInitPos, new Vector2D(velocity , 0), new Vector2D(0, 0), 20);
        
        //add to list to be updated
        circleList.add(plane);
        circleList.add(target);
        circleList.add(bomb);
        //place images on objects
        plane.getCircle().setFill(AssetManagerBomb.getPlaneImage());
        target.getCircle().setFill(AssetManagerBomb.getTargetImage());
        bomb.getCircle().setFill(AssetManagerBomb.getBombImage());
        //add to the visible game
        addToPane(plane.getCircle());
        addToPane(target.getCircle());
        addToPane(bomb.getCircle());
        
        
        
        
        
        
        
        
        timer = new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                
                 if (timeRunning) {
                   
                   // Time calculation 
                    double currentTime = (now - initialTime) / 1000000000.0;
                    double  frameDeltaTime = currentTime - lastFrameTime;
                    lastFrameTime = currentTime;
                    timeElapsed += frameDeltaTime;
                    
                   
                    
                    if(circleList.get(0).getPosition().getX() > AIlaunchCoding ) {
                        if(circleList.get(2).getAcceleration().getY() == 0){
                            circleList.get(2).setAcceleration(new Vector2D(0, 9.8));
                        }
                    }
                    
                    distance = Vector2D.distance(bomb.getPosition(), target.getPosition());

                    if(bomb.getPosition().getY() > target.getPosition().getY()) {
                        finish();
                    }
                    // Update existing circles (position)
                    if (frameDeltaTime < 0.20) {
                        circleList.forEach((obj) -> {
                            obj.update(frameDeltaTime);
                            plotChart(currentTime, series);
                        }); 
                    } 
                  
                    
                    
                }
                else {
                    
                    stop();
                    BtempPos = bomb.getPosition();
                    BtempVel = bomb.getVelocity();
                    PtempPos = plane.getPosition();
                }
            }
        };
    }
}
