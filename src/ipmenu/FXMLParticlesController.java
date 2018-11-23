/*
 * The lowest point on the graph is the initial position. Then the highest is the actual possition. Dince this is called as in a loop, it is pirnting the initial pos and the final multiple times
 * get rid of the initial position.
 * Right now you are plotting the position of a single particle and the time. This way you can see the velocity.
 */
package ipmenu;

import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author cstuser
 */
public class FXMLParticlesController implements Initializable {
    
    private double lastFrameTime = 0.0;
    private double mouseX;
    private double mouseY;
    private static int index;
    
    private ArrayList<ElectricFields> electricFieldsList;
    
    private ArrayList<Particles> particlesList;
    private Particles aParticle;
    private final float MASS = (float) 1;//(9.1*(Math.pow(10, -31))); //kg
    private final float CHARGE =(float) 0.01;//(10*(Math.pow(10, -6)));//micro coloumb
    
   
    private double currentTime;
    private double timeAfterStart;
    
    private boolean start;
    
    Vector2D finalSumE;
    Vector2D finalSumFe;

    
    
    @FXML
    public AnchorPane animationPane;
    
    @FXML
    private RadioButton positiveParticleRB;
    @FXML
    private RadioButton negativeParticleRB;

    @FXML
    private RadioButton electricFieldRB;
    
    @FXML
    private Button exitButton;
    @FXML
    private Button doneButton;
    
    @FXML
    private Button pauseButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button startButton;
    
    private AnimationTimer timer ;
    private long initialTime = System.nanoTime();
    public XYChart.Series series = new XYChart.Series();
    private double tempTime;
    
    @FXML
    private Label msgLabel;
    
    @FXML
    private NumberAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<Number, Number> chart;

    
    @FXML
    public void mousePosition(MouseEvent event){
        mouseX = event.getSceneX();//get x and y position
        
        mouseY = event.getSceneY();
    }
    @FXML
    public void mouseClicked(MouseEvent e) {

        if(positiveParticleRB.isSelected()){
            positiveParticle();
        }
        else if(negativeParticleRB.isSelected()){
            negativeParticle();
        }

    }
    

    private void negativeParticle() {
        msgLabel.setText("Place the particle somewhere in the space above.");
        
        Vector2D pos = new Vector2D(mouseX,mouseY);
        Vector2D vel = new Vector2D(0,0);
        Vector2D acc = new Vector2D(0,0);
        
        aParticle = new Particles(pos, MASS, (CHARGE *-1));
        particlesList.add(aParticle);
        addToPane(aParticle.getCircle());
    }
    
    private void positiveParticle() {
        msgLabel.setText("Place the particle somewhere in the space above.");
        Vector2D pos = new Vector2D(mouseX,mouseY);
       
        aParticle = new Particles(pos, MASS, CHARGE);
        particlesList.add(aParticle);
        addToPane(aParticle.getCircle());
    }
    
    
    
    public void addToPane(Node node)
    {
        animationPane.getChildren().add(node);
    }
    

    @FXML
    private void startButtonAction(ActionEvent event) {
        start = true;
        timer.start();
        msgLabel.setText("You can press \"pause\" to pause the simulation. ");
        pauseButton.setDisable(false);
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
       
           
        msgLabel.setText("Press \"start\" to continue with the simulation.");
        
    }
    
    @FXML
    private void helpButtonAction(ActionEvent event) {

        msgLabel.setText("To use this program, chose positive or negative particles and add them to the space above.\n"
                + "When you are ready, press start to see the particles' behaviour.\n"
                + "You can add more particles whenever you want.\n"
                + "Press \"pause\" to pause the simulation, \"done\" to go to the main menu and \"Exit\" to terminate the program");
        
    }
    
    public void plotChart(double t, XYChart.Series series){

          
          ArrayList<Double> times = new ArrayList<Double>();
          if((int)t%2==0){
              times.add(t);
              series.getData().add(new XYChart.Data(times.get(0),particlesList.get(0).position.magnitude() ));
              chart.getData().add(series);
          }
          
          
          
    }

   public static int getIndex(){
       return index;
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
                    
                       Particles.update(frameDeltaTime, particlesList, electricFieldsList, animationPane.getWidth(), animationPane.getHeight());
                       plotChart(currentTime, series);
                }
                else if(start == false){
                    stop();
                    //store variables
                }
                

            }
       };
   
   }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lastFrameTime = 0.0f;
        
        msgLabel.setText("Hello! Chose positive and negative particles and place them in the space above");
        particlesList = new ArrayList<Particles>();
        electricFieldsList = new ArrayList<ElectricFields>();
        
        AssetManager.preloadAllAssets();
       
       animationPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
       
        
       
        timer = new AnimationTimer(){
            @Override
            public void handle(long now) {
                 currentTime = (now - initialTime) / 1000000000.0;
                double  frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                
                   currentTime = 0; 
                   currentTime = (now - initialTime) / 1000000000.0;
                    
                    if(frameDeltaTime< 0.3){
                       Particles.update(frameDeltaTime, particlesList, electricFieldsList, animationPane.getWidth(), animationPane.getHeight());
                       plotChart(currentTime, series);
                    }
    
            }
        
        };
        
        

        
    }    
    
}
