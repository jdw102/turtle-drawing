package oolala.Models;

/**
 * A class to hold the X and Y position.
 * https://stackoverflow.com/questions/36701/struct-like-objects-in-java
 * According to Java Code Conventions, it is appropriate to have public instance variables,
 * if the class is essentially a data structure, with no behavior, the same effect as a struct in C++.
 *
 * @author Luyao Wang
 */
public class Position {
    public double posX;
    public double posY;
    public Position(double x, double y){
        posX = x;
        posY = y;
    }
}
