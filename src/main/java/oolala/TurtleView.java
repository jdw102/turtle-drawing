package oolala;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A turtle/cursor to draw things with.
 *
 * @author Aditya Paul
 */
public class TurtleView {
  private TurtleModel model;
  public static final double DEFAULT_ICON_SIZE = 30;
  public String turtleImage = "Images/turtleicon.png";
  public ImageView icon;
  public ArrayList<ImageView> stamps;
  private double iconSize;
  private Tooltip position;
  private final double TURTLE_SPEED = 100;

  public TurtleView( int posX, int posY, CanvasScreen screen){
    this.iconSize = DEFAULT_ICON_SIZE;
    this.model = new TurtleModel(posX, posY, screen.getBorderRectangle(), iconSize);
    this.position = new Tooltip();
    this.icon = createIcon(model.getPosX(), model.getPosY(), iconSize, screen, position, false);
    this.stamps = new ArrayList<>();
  }
  public void move(double dist, CanvasScreen canvas, SequentialTransition animation){
    Line path = model.createTurtlePath(dist, canvas);
    animation.getChildren().add(createPathAnimation(path, canvas));
  }
  public void rotateTurtle(int newAngle, SequentialTransition animation){
    RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
    rotate.setByAngle(newAngle);
    model.rotate(newAngle);
    animation.getChildren().add(rotate);
  }
  public void showTurtle(SequentialTransition animation){
    FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
    fade.setFromValue(icon.getOpacity());
    fade.setToValue(1.0);
    fade.setOnFinished(event -> icon.toFront());
    animation.getChildren().add(fade);
  }
  public void hideTurtle(SequentialTransition animation){
    FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
    fade.setFromValue(icon.getOpacity());
    fade.setToValue(0.0);
    fade.setOnFinished(event -> icon.toBack());
    animation.getChildren().add(fade);
  }
  public void home(SequentialTransition animation){
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), icon);
    fadeOut.setFromValue(icon.getOpacity());
    fadeOut.setToValue(0.0);
    model.moveHome();
    fadeOut.setOnFinished(event -> moveIcon(model.getHomeX(), model.getHomeY()));
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), icon);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(icon.getOpacity());
    animation.getChildren().add(fadeOut);
    animation.getChildren().add(fadeIn);
  }
  public void stamp(CanvasScreen canvas, SequentialTransition animation){
    ImageView s = createIcon(model.getPosX(), model.getPosY(), iconSize, canvas, new Tooltip(), true);
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
    model.updateRelativePosition(icon, position);
  }
  private ImageView createIcon(double x, double y, double size, CanvasScreen canvas, Tooltip tooltip, boolean stamp){
    ImageView i = new ImageView(new Image(turtleImage));
    i.setFitHeight(size);
    i.setFitWidth(size);
    i.setX(x- size / 2);
    i.setY(y- size / 2);
    i.setRotate(-model.geAngle());
    i.toFront();
    if (!stamp){installPositionLabel(i, tooltip);}
    return i;
  }
  public void installPositionLabel(ImageView i, Tooltip tooltip){
    i.setOnMouseEntered(event -> onHover());
    i.setOnMouseExited(event -> offHover());
    model.updateRelativePosition(i, tooltip);
    Tooltip.install(i, tooltip);
  }
  private void onHover(){
    icon.setScaleX(1.1);
    icon.setScaleY(1.1);
  }
  private void offHover(){
    icon.setScaleX(1 / 1.1);
    icon.setScaleY(1 / 1.1);
  }
  public void putPenUp(){
    model.putPenUp();
  }
  public void putPenDown(){
    model.putPenDown();
  }
  private PathTransition createPathAnimation(Line line, CanvasScreen canvas){
    double distance = Math.sqrt(Math.pow((line.getEndX() - line.getStartX()), 2) + Math.pow((line.getEndY() - line.getStartY()), 2) );
    Circle pen = new Circle(canvas.getThickness() / 2);
    pen.setFill(canvas.getBrushColor());
    Collection<Circle> penPoints = new ArrayList<>();
    boolean show = model.isPenDown();
    pen.translateXProperty().addListener((ov, t, t1) -> drawDots(pen, penPoints, canvas, show));
    pen.translateYProperty().addListener((ov, t, t1) -> drawDots(pen, penPoints, canvas, show));
    PathTransition pathTransition = new PathTransition(Duration.seconds(distance / TURTLE_SPEED), line, pen);
    pathTransition.setOnFinished(event -> removeDots(pen, penPoints, canvas, line, show));
    return pathTransition;
  }
  private void drawDots(Circle pen, Collection<Circle> penPoints, CanvasScreen canvas, boolean show){
    moveIcon(pen.getTranslateX(), pen.getTranslateY());
    Circle newCirc = new Circle(pen.getTranslateX(), pen.getTranslateY(), pen.getRadius(), pen.getFill());
    newCirc.setVisible(show);
    canvas.getShapes().getChildren().add(1, newCirc);
    penPoints.add(newCirc);
  }
  private void removeDots(Circle pen, Collection<Circle> penPoints, CanvasScreen canvas, Line line, boolean show){
    canvas.getShapes().getChildren().removeAll(penPoints);
    penPoints.removeAll(penPoints);
    line.setVisible(show);
    canvas.getShapes().getChildren().add(1, line);
  }
}

