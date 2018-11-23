/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import ipmenu.GameObjectBomb;

/**
 *
 * @author cstuser
 */
public class Bomb extends GameObjectBomb {
    protected double rotation = 0;
    
    public Bomb(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
       super(position, velocity, acceleration, radius);
    }
     public void update(double dt)
    {
        super.update(dt);
        
        if (acceleration.getY() != 0 )
        {
            rotation = 1/ velocity.getY() ;
            circle.rotateProperty().add(rotation);
        }
        
    }
}
