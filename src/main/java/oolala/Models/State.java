package oolala.Models;

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
