package oolala;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A turtle/cursor to draw things with.
 *
 * @author Aditya Paul
 */
public class Turtle {

  public static final int DEFAULT_ID = 0; //TODO: Will we init this in the canvas?
  public static final int HOME_X = 400; //TODO: Will we init this in the canvas?
  public static final int HOME_Y = 400; //TODO: Will we init this in the canvas?
  public static final int DEFAULT_ANGLE = 0;
  public static final Color DEFAULT_COLOR = Color.BLACK;
  public String turtleImage = "C:\\Users\\User\\IdeaProjects\\oolala_team09\\src\\main\\resources\\examples\\turtleicon.png";
  public ImageView icon;

  private int id; //TODO: Is this variable needed?
  private double posX;
  private double posY;
  private int angle;
  private boolean penDown;
  private Color color;

  public Turtle() {
    this.id = DEFAULT_ID;
    this.posX = HOME_X;
    this.posY = HOME_Y;
    this.angle = DEFAULT_ANGLE;
    this.color = Color.BLACK;
    this.icon = new ImageView(new Image(turtleImage));
    this.icon.setFitHeight(40);
    this.icon.setFitWidth(40);
    this.icon.setX(HOME_X - this.icon.getFitWidth() / 2);
    this.icon.setY(HOME_Y - this.icon.getFitHeight() / 2);

  }
  public Turtle(int id, int posX, int posY, int angle){
    this.id = id;
    this.posX = posX;
    this.posY = posY;
    this.angle = angle;
    this.penDown = true;
  }

  public void readInstruction(Command command, OolalaView display){
    switch(command.prefix){
      case FORWARD -> moveForward(command.param, display);
      case BACK -> moveBack(command.param, display);
      case LEFT -> leftTurn(command.param);
      case RIGHT -> rightTurn(command.param);
    }
  }

  public void moveForward(int dist, OolalaView display){
    System.out.println(dist);
    display.drawLine(this.posX, this.posY, dist, this.angle + 90);
    this.posY = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
    this.posX = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
    moveIcon();
  }
  public void moveBack(int dist, OolalaView display){
    System.out.println(dist);
    display.drawLine(this.posX, this.posY, dist, this.angle + 270);
    this.posY = this.posY - dist * Math.sin(Math.toRadians(this.angle + 270));
    this.posX = this.posX + dist * Math.cos(Math.toRadians(this.angle + 270));
    moveIcon();
  }
  public void leftTurn(int newAngle){
    angle = newAngle;
    rotateIcon();
  }
  public void rightTurn(int newAngle){
    angle = - newAngle;
    rotateIcon();
  }
  public void resetTurtle(){
    this.posX = HOME_X;
    this.posY = HOME_Y;
    this.angle = DEFAULT_ANGLE;
    moveIcon();
    rotateIcon();
  }
  public Node getIcon(){
    return icon;
  }
  private void moveIcon(){
    this.icon.setX(this.posX - this.icon.getFitWidth() / 2);
    this.icon.setY(this.posY - this.icon.getFitHeight() / 2);
  }
  private void rotateIcon(){
    this.icon.setRotate(-angle);
  }
}
