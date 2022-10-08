package oolala.Models;

/**
 * State class extending Position class.
 * Used to store turtle states when pushed to stack.
 *
 * @author Aditya Paul
 */
public class State extends Position{
  public double angle;
  public State(double x, double y, double ang){
    super(x, y);
    angle = ang;
  }

  public State(TurtleModel t){
    super(t.getRelPos());
    angle = t.getAngle();
  }
}
