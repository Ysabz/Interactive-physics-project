/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipmenu;

import java.util.ArrayList;

/**
 *
 * @author Tere
 */
public class ElectricFields {
    //constants 
    private final static double e = 2.718281828;
    private final static double k = 9* Math.pow(10, 9);
    
    //attributes of an electric field vector
    private double length;
    private double lengthX;
    private double lengthY;
    //private double magnitude;
    
    protected Vector2D positionI;//position of the first point
    protected Vector2D positionF;//position of the second point
    
   
    
    
    public ElectricFields(Vector2D positionI, Vector2D positionF){
        this.positionI = new Vector2D(positionI.getX(), positionI.getY());
        this.positionF = new Vector2D(positionF.getX(), positionF.getY());
        
        this.lengthX = positionF.getX() - positionI.getX();
        this.lengthY = positionF.getY() - positionI.getY();
        this.length = Math.sqrt(Math.pow(lengthX, 2) + Math.pow(lengthY, 2));
        
        //draw the arow
    }
    
    public double getdx(){ return this.lengthX;}
    public double getdy(){return this.lengthY; }
    public Vector2D getPosI(){ return this.positionI;}
    public Vector2D getPosF(){ return this.positionF; }
    public double getLengthX(){ return this.lengthX; }
    public double getLengthY(){ return this.lengthY; }
    
    public static Vector2D netElectricField(ArrayList<Particles> particlesList, ArrayList<ElectricFields> electricFieldsList, Vector2D positionParticle,double radiusParticle){
        double eNetX = 0;
        double eNetY = 0;
        
        double dx, dy, d;
        
        //iterate thought all the electricFields
        if(electricFieldsList.isEmpty() == false){
            for (int i = 1; i < electricFieldsList.size(); i++) {
                dx = electricFieldsList.get(i).getPosF().getX() - positionParticle.getX();
                dy = electricFieldsList.get(i).getPosF().getY() - positionParticle.getY();

                d = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));

                //gaussian distribution
                eNetX += Math.pow(e, -d/100) * electricFieldsList.get(i).getLengthX() * 200;
                eNetY += Math.pow(e, -d/100) * electricFieldsList.get(i).getLengthY() * 200;
            }
            
        }
        FXMLParticlesController.getIndex();
        //System.out.println("no electric fields");
        
            for (int i = 0; i < particlesList.size(); i++) {
                    dx = positionParticle.getX() - particlesList.get(i).getPosition().getX();
                    dy = positionParticle.getY() - particlesList.get(i).getPosition().getY();
                    d = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));


                    //electric field due to the particlesList.get(i);
                    if (d > particlesList.get(i).getRadius() + radiusParticle) {
                        //System.out.println("                  if statement");
                        double  E = k*particlesList.get(i).getCharge()/(Math.pow(d, 2)); 
                        eNetX += E * (dx/d);
                        eNetY += E * (dy/d);
                    }
                //}
//                if( i < particlesList.size()){i = 0;}
            }
//            System.out.println("In class, eNetX " + eNetX);
//            System.out.println("In class, eNetY " + eNetY);
        
        return new Vector2D(eNetX, eNetY);
    }
}
