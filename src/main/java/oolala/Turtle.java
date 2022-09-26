package oolala;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * A turtle/cursor to draw things with.
 *
 * @author Aditya Paul
 */
public class Turtle {

  public static final int DEFAULT_ID = 0; //TODO: Will we init this in the canvas?
  public double homeX = 0; //TODO: Will we init this in the canvas?
  public double homeY = 0; //TODO: Will we init this in the canvas?
  public static final int DEFAULT_THICKNESS = 3;
  public static final int DEFAULT_ANGLE = 0;
  public static final Color DEFAULT_COLOR = Color.BLACK;
  public static final double DEFAULT_ICON_SIZE = 30;
  public String turtleImage = "Images/turtleicon.png";
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
  private double xMax;
  private double xMin;
  private double yMax;
  private double yMin;


  public Turtle() {
    this.id = DEFAULT_ID;
    this.posX = 0;
    this.posY = 0;
    this.angle = DEFAULT_ANGLE;
    this.color = Color.BLACK;
    this.penDown = true;
    this.thickness = DEFAULT_THICKNESS;
    this.iconSize = DEFAULT_ICON_SIZE;
    this.icon = createIcon(this.posX, this.posY, iconSize);
    this.stamps = new ArrayList<>();
    Rectangle r = new Rectangle();
    calcBounds(r);

  }
  public Turtle(int id, int posX, int posY, Rectangle r){
    System.out.println(r.getWidth());
    homeX = posX + r.getX() + r.getWidth()/2;
    homeY = posY + r.getY() + r.getHeight()/2;
    this.id = id;
    this.posX = homeX;
    this.posY = homeY;
    this.angle = DEFAULT_ANGLE;
    this.penDown = true;
    this.color = Color.BLACK;
    this.thickness = DEFAULT_THICKNESS;
    this.iconSize = DEFAULT_ICON_SIZE;
    this.icon = createIcon(this.posX, this.posY, iconSize);
    this.stamps = new ArrayList<>();
    calcBounds(r);
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
    double x = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
    double y = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
    if (x > xMax){
      x = xMax;
    }
    if (x < xMin){
      x = xMin;
    }
    if (y > yMax){
      y = yMax;
    }
    if (y < yMin){
      y = yMin;
    }
    if (penDown){
      display.getCanvasScreen().drawLine(this.posX, this.posY, x, y, thickness, color);
    }
    this.posY = y;
    this.posX = x;
    moveIcon();
  }
  public void moveBack(int dist, OolalaView display){
    double x = this.posX - dist * Math.cos(Math.toRadians(this.angle + 90));
    double y = this.posY + dist * Math.sin(Math.toRadians(this.angle + 90));
    if (x > xMax){
      x = xMax;
    }
    if (x < xMin){
      x = xMin;
    }
    if (y > yMax){
      y = yMax;
    }
    if (y < yMin){
      y = yMin;
    }
    if (penDown){
      display.getCanvasScreen().drawLine(this.posX, this.posY, x, y, thickness, color);
    }
    this.posY = y;
    this.posX = x;
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
    this.posX = homeX;
    this.posY = homeY;
    this.angle = DEFAULT_ANGLE;
    moveIcon();
    rotateIcon();
  }
  public void stamp(OolalaView view){
    ImageView s = createIcon(this.posX, this.posY, iconSize);
    stamps.add(s);
    view.getCanvasScreen().getShapes().getChildren().add(s);
  }
  public Node getIcon(){
    return icon;
  }
  private void moveIcon(){
    this.icon.setX(this.posX - iconSize / 2);
    this.icon.setY(this.posY - iconSize / 2);
    this.icon.toFront();
  }
  private void rotateIcon(){
    this.icon.setRotate(-angle);
  }
  public void resetTurtle(OolalaView view){
    home();
    view.getCanvasScreen().getShapes().getChildren().removeAll(stamps);
    stamps.removeAll(stamps);
  }
  private ImageView createIcon(double x, double y, double size){
    ImageView i = new ImageView(new Image(turtleImage));
    i.setFitHeight(size);
    i.setFitWidth(size);
    i.setX(x- size / 2);
    i.setY(y- size / 2);
    i.setRotate(-angle);
    i.toFront();
    System.out.println("Creating icon");
    return i;
  }
  private void calcBounds(Rectangle r){
    xMin = r.getX();
    xMax = r.getX() + r.getWidth();
    yMin = r.getY();
    yMax = r.getY() + r.getHeight();
  }
}

