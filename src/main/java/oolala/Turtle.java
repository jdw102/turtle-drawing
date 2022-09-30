package oolala;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
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
    this.position = new Tooltip();
    this.icon = createIcon(this.posX, this.posY, iconSize, screen, position, false);
    this.stamps = new ArrayList<>();
    calcBounds(screen.getBorderRectangle());
  }

  public void readInstruction(Command command, CanvasScreen canvas, SequentialTransition animation){
    switch(command.prefix){
      case FORWARD -> moveForward(command.param, canvas, animation);
      case BACK -> moveBack(command.param, canvas, animation);
      case LEFT -> leftTurn(command.param, animation);
      case RIGHT -> rightTurn(command.param, animation);
      case PENDOWN -> putPenDown();
      case PENUP -> putPenUp();
      case SHOWT -> showTurtle(animation);
      case HIDET -> hideTurtle(animation);
      case HOME -> home(animation);
      case STAMP -> stamp(canvas, animation);
    }
  }

  public void moveForward(double dist, CanvasScreen canvas, SequentialTransition animation){
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
    drawLine(this.posX, this.posY, x, y, canvas, animation);
    posX = x;
    posY = y;
  }
  public void moveBack(int dist, CanvasScreen canvas, SequentialTransition animation){
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
    drawLine(this.posX, this.posY, x, y, canvas, animation);
    posX = x;
    posY = y;
  }
  public void leftTurn(int newAngle, SequentialTransition animation){
    RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
    rotate.setByAngle(-newAngle);
    angle += newAngle;
    animation.getChildren().add(rotate);
  }
  public void rightTurn(int newAngle, SequentialTransition animation){
    RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
    rotate.setByAngle(newAngle);
    angle -= newAngle;
    animation.getChildren().add(rotate);
  }
  public void putPenDown(){
    penDown = true;
  }
  public void putPenUp(){
    penDown = false;
  }
  public void showTurtle(SequentialTransition animation){
    FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
    fade.setFromValue(icon.getOpacity());
    fade.setToValue(1.0);
    animation.getChildren().add(fade);
  }
  public void hideTurtle(SequentialTransition animation){
    FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
    fade.setFromValue(icon.getOpacity());
    fade.setToValue(0.0);
    animation.getChildren().add(fade);
  }
  public void home(SequentialTransition animation){
    this.posX = homeX;
    this.posY = homeY;
    this.angle = DEFAULT_ANGLE;
    moveIcon(homeX, homeY);
//    rotateIcon();
  }
  public void stamp(CanvasScreen canvas, SequentialTransition animation){
    ImageView s = createIcon(this.posX, this.posY, iconSize, canvas, new Tooltip(), true);
    s.toFront();
    stamps.add(s);
    s.setOpacity(0.0);
    canvas.getShapes().getChildren().add(s);
    FadeTransition fade = new FadeTransition(Duration.seconds(0.25), s);
    fade.setFromValue(0.0);
    fade.setToValue(1.0);
    animation.getChildren().add(fade);
  }
  public Node getIcon(){
    return icon;
  }
  private void moveIcon(double x, double y){
    this.icon.setX(x - iconSize / 2);
    this.icon.setY(y - iconSize / 2);
    this.icon.toFront();
    updateRelativePosition(icon, position);
  }
//  private void rotateIcon(){
//
//    this.icon.setRotate(-angle);
//  }
//  public void resetTurtle(AppView view){
//    home();
//    view.getCanvasScreen().getShapes().getChildren().removeAll(stamps);
//    stamps.removeAll(stamps);
//  }
  private ImageView createIcon(double x, double y, double size, CanvasScreen canvas, Tooltip tooltip, boolean stamp){
    ImageView i = new ImageView(new Image(turtleImage));
    i.setFitHeight(size);
    i.setFitWidth(size);
    i.setX(x- size / 2);
    i.setY(y- size / 2);
    i.setRotate(-angle);
    i.toFront();
    if (!stamp){installPositionLabel(i, tooltip);}
    System.out.println("Creating icon");
    return i;
  }
  public void installPositionLabel(ImageView i, Tooltip tooltip){
    i.setOnMouseEntered(event -> onHover());
    i.setOnMouseExited(event -> offHover());
    updateRelativePosition(i, tooltip);
    Tooltip.install(i, tooltip);
  }
  private void calcBounds(Rectangle r){
    xMin = r.getX() + iconSize / 2;
    xMax = r.getX() + r.getWidth() - iconSize / 2;
    yMin = r.getY() + iconSize / 2;
    yMax = r.getY() + r.getHeight() - iconSize / 2;
  }
  private void updateRelativePosition(ImageView i, Tooltip tooltip){
    relX = i.getX() - border.getX();
    relY = (border.getY() + border.getHeight()) - i.getY();
    tooltip.setText("x: " + Double.toString(Math.round(relX)) + " y: " + Double.toString(Math.round(relY)));
  }
  private void onHover(){
    icon.setScaleX(1.1);
    icon.setScaleY(1.1);
  }
  private void offHover(){
    icon.setScaleX(1 / 1.1);
    icon.setScaleY(1 / 1.1);
  }
//  private void createPath(double xStart, double yStart, double xEnd, double yEnd, CanvasScreen screen){
//    lines.add(screen.drawLine(xStart, yStart, xEnd, yEnd));
//    path.getElements().add(new MoveTo(xStart, yStart));
//    path.getElements().add(new LineTo(xStart, yStart));
//  }
//  private void followPath(){
//    Circle pen  = new Circle(THICKNESS / 2);
//    pen.setFill(Color.BLACK);
//    ArrayList<Circle> penPoints = new ArrayList<>();
//    ChangeListener changeListener = new ChangeListener() {
//      @Override
//      public void changed(ObservableValue ov, Object t, Object t1) {
//        Circle newCirc = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius());
//        shapes.getChildren().add(1, newCirc);
//        penPoints.add(newCirc);
//      }
//    };
//    pen.translateXProperty().addListener(changeListener);
//    pen.translateYProperty().addListener(changeListener);
//    pathTransition.setOnFinished(event -> {
//      shapes.getChildren().removeAll(penPoints);
//      penPoints.removeAll(penPoints);
//      shapes.getChildren().add(1, line);
//    });
//  }
  public void drawLine(double xStart, double yStart, double xEnd, double yEnd, CanvasScreen canvas, SequentialTransition animation) {
    System.out.println(xStart + " " + yStart);
    System.out.println(xEnd + " " + yEnd);
    Line line = new Line();
    line.setStartX(xStart);
    line.setStartY(yStart);
    line.setEndX(xEnd);
    line.setEndY(yEnd);
    line.setStrokeWidth(canvas.getThickness());
    line.setStroke(canvas.getBrushColor());

    PathTransition pathTransition = penDown ? penDownAnimation(line, canvas) : penUpAnimation(line, canvas);

    //TODO: is color an attribute of a turtle?
    animation.getChildren().add(pathTransition);
  }
  private PathTransition penDownAnimation(Line line, CanvasScreen canvas){
    Circle pen = new Circle(canvas.getThickness() / 2);
    pen.setFill(canvas.getBrushColor());
    pen.setVisible(false);
    ArrayList<Circle> penPoints = new ArrayList<>();
    ChangeListener changeListener = new ChangeListener() {
      boolean first = true;
      @Override
      public void changed(ObservableValue ov, Object t, Object t1) {
        if (!first){
          moveIcon(pen.getTranslateX(), pen.getTranslateY());
          Circle newCirc = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius());
          canvas.getShapes().getChildren().add(1, newCirc);
          penPoints.add(newCirc);
        }
        else{
          first = false;
        }
      }
    };
    pen.translateXProperty().addListener(changeListener);
    pen.translateYProperty().addListener(changeListener);
    PathTransition pathTransition = new PathTransition(Duration.seconds(2), line, pen);
    pathTransition.setOnFinished(event -> {
      canvas.getShapes().getChildren().removeAll(penPoints);
      penPoints.removeAll(penPoints);
      canvas.getShapes().getChildren().add(1, line);
    });
    return pathTransition;
  }
  private PathTransition penUpAnimation(Line line, CanvasScreen canvas){
    Circle pen = new Circle(canvas.getThickness() / 2);
    pen.setVisible(false);
    pen.setFill(canvas.getBrushColor());
    ArrayList<Circle> penPoints = new ArrayList<>();
    ChangeListener changeListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue ov, Object t, Object t1) {
        moveIcon(pen.getTranslateX(), pen.getTranslateY());
      }
    };
    pen.translateXProperty().addListener(changeListener);
    pen.translateYProperty().addListener(changeListener);
    PathTransition pathTransition = new PathTransition(Duration.seconds(2), line, pen);
    return pathTransition;
  }
}

