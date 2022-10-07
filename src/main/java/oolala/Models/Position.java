package oolala.Models;

public class Position {
    private double posX;
    private double posY;
    public Position(double x, double y){
        posX = x;
        posY = y;
    }
    public double getPosX(){
        return posX;
    }
    public double getPosY(){
        return posY;
    }
}
