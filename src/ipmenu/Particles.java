/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.util.ArrayList;
import javafx.scene.shape.Circle;

/**
 *
 * @author Tere
 */
public class Particles {
    //constants 
    public static final double RADIUS = 50;
    private final double e = 2.718281828;
    private final static double k = 9* Math.pow(10, 9);
   
    //attributes 
    private double charge;
    private double mass;
    private double vx = 0;
    private double vy = 0;
    public static double potentialEnergy;
    
    //other 
    protected Circle circle;
    protected Vector2D position = new Vector2D(0,0);
    protected Vector2D acceleration = new Vector2D(0,0);
    
    public static ArrayList<String> netElectricFieldsX;
    
    public static ArrayList<Vector2D> netElectricFields;
    public static ArrayList<Vector2D> netElectricForces;
    
    
    //constructor
    public Particles( Vector2D position, float mass, float charge){
        this.position = new Vector2D(position.getX(), position.getY());//position;
        this.mass = mass;
        this.charge = charge;
        
        circle = new Circle(0.0, 0.0, RADIUS);
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
        
        if (charge > 0) {
           circle.setFill(AssetManager.getPosPartImg()); 
        }
        else if (charge < 0) {
            circle.setFill(AssetManager.getNegPartImg());
        }
        
        
    }
    
    
    
    public Vector2D getPosition(){ return this.position;}

    public double getRadius(){ return this.RADIUS;}
    public double getCharge(){ return this.charge; }
    public double getMass(){ return this.mass;} 
    public double getvx(){ return this.vx;}
    public double getvy(){ return this.vy;}
    public Circle getCircle(){return circle;}
    
    //mutators 
    public void setvx( double newVel){ this.vx = newVel;}
    public void setvy( double newVel){ this.vy = newVel;}
    public void setPosition (Vector2D newPos){ this.position = newPos;}
    public void setPositionX (double newPos){ this.position.setX(newPos);}
    public void setPositionY (double newPos){ this.position.setY(newPos);}
    
    
    //updates all of the particles at the same time
    public static void update(double dt, ArrayList<Particles> particlesList, ArrayList<ElectricFields> electricFieldsList, double width, double height){
        netElectricFieldsX = new ArrayList<String>();  
        netElectricFields = new ArrayList<Vector2D>();
        netElectricForces = new ArrayList<Vector2D> ();
        
            for (int i = 0; i < particlesList.size(); i++) {

                Particles currentParticle = particlesList.get(i);
                Vector2D netField = ElectricFields.netElectricField(particlesList, electricFieldsList,currentParticle.position, RADIUS); 
                netElectricFields.add(netField);
                double xValue = netField.getX();
                String xStrValue = Double.toString(xValue);
                netElectricFieldsX.add(xStrValue);
                double accelerationIX = currentParticle.charge* netField.getX()/currentParticle.mass;  //acceleration of this particle on the x axes 
                double accelerationIY = currentParticle.charge * netField.getY()/currentParticle.mass;
                Vector2D force = new Vector2D(accelerationIX/currentParticle.mass, accelerationIY/currentParticle.mass);
                netElectricForces.add(force);
                
                currentParticle.setvx(currentParticle.vx + accelerationIX * dt);
                currentParticle.setvy(currentParticle.vy + accelerationIY * dt);

            }
            //elastic collisions between each particle
            for (int i = 0; i < particlesList.size(); i++) {
                Particles currentParticle = particlesList.get(i);

                if (particlesList.size() > 1) {
                    
                    for (int j = i + 1; j < particlesList.size(); j++) {

                            double dx = particlesList.get(j).getPosition().getX()  - currentParticle.position.getX();
                            double dy = particlesList.get(j).getPosition().getY() - currentParticle.position.getY();
                            double d = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));

                            //potential energy
                            potentialEnergy =+(k * (currentParticle.getCharge() * particlesList.get(j).getCharge())/d);
                            
                            
                            double dvx = particlesList.get(j).getvx() - currentParticle.vx;
                            double dvy = particlesList.get(j).getvy() - currentParticle.vy;


                            double velDotDis = dvx * dx + dvy * dy;

                            if (d <= (2*RADIUS) && velDotDis < 0) {
                                double disSquared = dx*dx + dy*dy;

                                double lambda = -2*currentParticle.mass  * particlesList.get(j).getMass()/(currentParticle.mass + particlesList.get(j).getMass())*velDotDis/disSquared;

                                //modification of th velocities 
                                currentParticle.setvx(currentParticle.vx - (lambda/(currentParticle.mass)*dx));
                                currentParticle.setvy(currentParticle.vy- (lambda/(currentParticle.mass)*dy));
                                particlesList.get(j).setvx(particlesList.get(j).getvx()+ (lambda/(particlesList.get(j).getMass())*dx));
                                particlesList.get(j).setvy(particlesList.get(j).getvy()+ (lambda/(particlesList.get(j).getMass())*dy));
                            }

                    }
                }
                
                currentParticle.setPositionX(currentParticle.position.getX() + currentParticle.vx * dt);
                currentParticle.setPositionY(currentParticle.position.getY() + currentParticle.vy * dt);

                currentParticle.circle.setLayoutX(currentParticle.position.getX());
                currentParticle.circle.setLayoutY(currentParticle.position.getY());
                //elastic collisions: boundaries
                if (currentParticle.position.getX() + RADIUS >= width){
                    currentParticle.setvx(currentParticle.vx * -1);
                    currentParticle.setPositionX(width - RADIUS); 
                } else if (currentParticle.position.getX() - RADIUS <= 0){ 
                    currentParticle.setvx(currentParticle.vx * -1);
                    currentParticle.setPositionX(RADIUS);
                }
                if (currentParticle.position.getY() + RADIUS >= height) {
                    currentParticle.setvy(currentParticle.vy * -1);
                    currentParticle.setPositionY(height -RADIUS); 
                } else if (currentParticle.position.getY() - RADIUS <= 0){ 
                    currentParticle.setvy(currentParticle.vy * -1);
                    currentParticle.setPositionY(RADIUS);
                }
                
                
            }

    }
        
     
    
} 
