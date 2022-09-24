package oolala;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * A turtle/cursor to draw things with.
 *
 * @author Aditya Paul
 */
public class Turtle {

  public static final int DEFAULT_ID = 0; //TODO: Will we init this in the canvas?
  public static final int HOME_X = 0; //TODO: Will we init this in the canvas?
  public static final int HOME_Y = 0; //TODO: Will we init this in the canvas?
  public static final int DEFAULT_THICKNESS = 3;
  public static final int DEFAULT_ANGLE = 0;
  public static final Color DEFAULT_COLOR = Color.BLACK;
  public static final double DEFAULT_ICON_SIZE = 30;
  public String turtleImage = "C:\\Users\\User\\IdeaProjects\\oolala_team09\\src\\main\\resources\\Images\\turtleicon.png";
  public ImageView icon;
  public ArrayList<ImageView> stamps;

  private int id; //TODO: Is this variable needed?
  private double posX;
  private double posY;
  private int angle;
  private boolean penDown;
  private Color color;
  private double thickness;
  private double iconSize;

  public Turtle() {
    this.id = DEFAULT_ID;
    this.posX = HOME_X;
    this.posY = HOME_Y;
    this.angle = DEFAULT_ANGLE;
    this.color = Color.BLACK;
    this.penDown = true;
    this.thickness = DEFAULT_THICKNESS;
    this.iconSize = DEFAULT_ICON_SIZE;
    this.icon = createIcon(this.posX, this.posY, iconSize);
    this.stamps = new ArrayList<>();

  }
  public Turtle(int id, int posX, int posY, int angle){
    this.id = id;
    this.posX = posX;
    this.posY = posY;
    this.angle = angle;
    this.penDown = true;
    this.color = Color.BLACK;
    this.thickness = DEFAULT_THICKNESS;
    this.iconSize = DEFAULT_ICON_SIZE;
    this.icon = createIcon(this.posX, this.posY, iconSize);
    this.stamps = new ArrayList<>();
  }

  public void readInstruction(Command command, OolalaView display){
    switch(command.prefix){
      case FORWARD -> moveForward(command.param, display);
      case BACK -> moveBack(command.param, display);
      case LEFT -> leftTurn(command.param);
      case RIGHT -> rightTurn(command.param);
      case PENDOWN -> putPenDown();
      case PENUP -> putPenUp();
      case SHOWT -> showTurtle();
      case HIDET -> hideTurtle();
      case HOME -> home();
      case STAMP -> stamp(display);
    }
  }

  public void moveForward(int dist, OolalaView display){
    if (penDown){
      display.drawLine(this.posX, this.posY, dist, this.angle + 90, thickness, color);
    }
    this.posY = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
    this.posX = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
    moveIcon();
  }
  public void moveBack(int dist, OolalaView display){
    if (penDown){
      display.drawLine(this.posX, this.posY, dist, this.angle + 270, thickness, color);
    }
    this.posY = this.posY - dist * Math.sin(Math.toRadians(this.angle + 270));
    this.posX = this.posX + dist * Math.cos(Math.toRadians(this.angle + 270));
    moveIcon();
  }
  public void leftTurn(int newAngle){
    angle += newAngle;
    rotateIcon();
  }
  public void rightTurn(int newAngle){
    angle -= newAngle;
    rotateIcon();
  }
  public void putPenDown(){
    penDown = true;
  }
  public void putPenUp(){
    penDown = false;
  }
  public void showTurtle(){
    icon.setVisible(true);
  }
  public void hideTurtle(){
    icon.setVisible(false);
  }
  public void home(){
    this.posX = HOME_X;
    this.posY = HOME_Y;
    this.angle = DEFAULT_ANGLE;
    moveIcon();
    rotateIcon();
  }
  public void stamp(OolalaView view){
    ImageView s = createIcon(this.posX, this.posY, iconSize);
    stamps.add(s);
    view.getCanvas().getChildren().add(s);
  }
  public Node getIcon(){
    return icon;
  }
  private void moveIcon(){
    this.icon.setX(this.posX - iconSize / 2);
    this.icon.setY(this.posY - iconSize / 2);
  }
  private void rotateIcon(){
    this.icon.setRotate(-angle);
  }
  public void resetTurtle(OolalaView view){
    home();
    view.getCanvas().getChildren().removeAll(stamps);
    stamps.removeAll(stamps);
  }
  private ImageView createIcon(double x, double y, double size){
    ImageView i = new ImageView(new Image(turtleImage));
    i.setFitHeight(size);
    i.setFitWidth(size);
    i.setX(x- size / 2);
    i.setY(y- size / 2);
    i.setRotate(-angle);
    return i;
  }
}