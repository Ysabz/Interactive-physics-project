package ipmenu;

import ipmenu.Vector2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class GameObjectPool {
    protected Circle circle;
    protected Vector2D position;
    protected Vector2D velocity;
    protected Vector2D acceleration;
    protected double angle;
    
    public GameObjectPool(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius, double angle){
        this.angle = angle;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration; 
        
        circle = new Circle(0.0, 0.0, radius);
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
        
    }
    
    public Vector2D getPosition(){
        return position;
    }
    public Vector2D getVelocity(){
        return velocity;
    }
    public Vector2D getAcceleration(){
        return acceleration;
    }
    public double getAngle(){
        return angle;
    }
    public void setPosition(Vector2D vector) {
        this.position = new Vector2D(vector.getX(), vector.getY());
    }
    public void setVelocity(Vector2D vector){
        this.velocity = new Vector2D(vector.getX(), vector.getY());
    }
    public void setAcceleration(Vector2D vector){
        this.acceleration = new Vector2D(vector.getX(), vector.getY());
    }
    public void setAngle(double value){
        this.angle = value;
    }
    public Circle getCircle(){
        return circle;
    }
        
    public void update(double dt) {
        // Euler Integration
        // Update velocity
        Vector2D frameAcceleration = acceleration.mul(dt);
        velocity = velocity.add(frameAcceleration);

        // Update position
        position = position.add(velocity.mul(dt));
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
        
    }
}
