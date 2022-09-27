package oolala;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
  private double iconSize;
  private double xMax;
  private double xMin;
  private double yMax;
  private double yMin;
  private double relX;
  private double relY;
  private Rectangle border;
  private Tooltip position;

  public Turtle() {
    this.id = DEFAULT_ID;
    this.posX = 0;
    this.posY = 0;
    this.angle = DEFAULT_ANGLE;
    this.penDown = true;
    this.iconSize = DEFAULT_ICON_SIZE;
    this.stamps = new ArrayList<>();
    Rectangle r = new Rectangle();
    calcBounds(r);

  }
  public Turtle(int id, int posX, int posY, CanvasScreen screen){
    border = screen.getBorderRectangle();
    homeX = posX + border.getX() + border.getWidth()/2;
    homeY = posY + border.getY() + border.getHeight()/2;
    this.id = id;
    this.posX = homeX;
    this.posY = homeY;
    this.angle = DEFAULT_ANGLE;
    this.penDown = true;
    this.iconSize = DEFAULT_ICON_SIZE;
    this.icon = createIcon(this.posX, this.posY, iconSize, screen);
    this.stamps = new ArrayList<>();
    calcBounds(screen.getBorderRectangle());
  }

  public void readInstruction(Command command, OolalaGame display){
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

  public void moveForward(double dist, OolalaGame display){
    double x = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
    double y = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
    if (x > xMax){
      double distX = xMax - this.posX;
      x = xMax;
      y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
    }
    if (x < xMin){
      double distX = xMin - this.posX;
      x = xMin;
      y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
    }
    if (y > yMax){
      double distY = this.posY - yMax;
      y = yMax;
      x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
    }
    if (y < yMin){
      double distY = this.posY - yMin;
      y = yMin;
      x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
    }
    if (penDown){
      display.getCanvasScreen().drawLine(this.posX, this.posY, x, y);
    }
    this.posY = y;
    this.posX = x;
    moveIcon();
  }
  public void moveBack(int dist, OolalaGame display){
    double x = this.posX - dist * Math.cos(Math.toRadians(this.angle + 90));
    double y = this.posY + dist * Math.sin(Math.toRadians(this.angle + 90));
    if (x > xMax){
      double distX = xMax - this.posX;
      x = xMax;
      y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
    }
    if (x < xMin){
      double distX = xMin - this.posX;
      x = xMin;
      y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
    }
    if (y > yMax){
      double distY = this.posY - yMax;
      y = yMax;
      x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
    }
    if (y < yMin){
      double distY = this.posY - yMin;
      y = yMin;
      x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
    }
    if (penDown){
      display.getCanvasScreen().drawLine(this.posX, this.posY, x, y);
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
  public void stamp(OolalaGame view){
    ImageView s = createIcon(this.posX, this.posY, iconSize, view.getCanvasScreen());
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
    updateRelativePosition();
  }
  private void rotateIcon(){
    this.icon.setRotate(-angle);
  }
  public void resetTurtle(OolalaGame view){
    home();
    view.getCanvasScreen().getShapes().getChildren().removeAll(stamps);
    stamps.removeAll(stamps);
  }
  private ImageView createIcon(double x, double y, double size, CanvasScreen canvas){
    ImageView i = new ImageView(new Image(turtleImage));
    i.setFitHeight(size);
    i.setFitWidth(size);
    i.setX(x- size / 2);
    i.setY(y- size / 2);
    i.setRotate(-angle);
    i.toFront();
    System.out.println("Creating icon");
    position = new Tooltip("x: " + Double.toString(Math.round(relX)) + " y: " + Double.toString(Math.round(relY)));
    Tooltip.install(i, position);
    return i;
  }
  private void calcBounds(Rectangle r){
    xMin = r.getX() + iconSize / 2;
    xMax = r.getX() + r.getWidth() - iconSize / 2;
    yMin = r.getY() + iconSize / 2;
    yMax = r.getY() + r.getHeight() - iconSize / 2;
  }
  private void updateRelativePosition(){
    relX = posX - border.getX();
    relY = (border.getY() + border.getHeight()) - posY;
    position.setText("x: " + Double.toString(Math.round(relX)) + " y: " + Double.toString(Math.round(relY)));
  }
}

