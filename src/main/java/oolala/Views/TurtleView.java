package oolala.Views;

import java.sql.SQLOutput;
import java.util.Objects;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;
import oolala.Models.AppModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Models.TurtleModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A turtle/cursor to draw things with.
 *
 * @author Aditya Paul
 */
public class TurtleView {
  private double homeX;
  private double homeY;
  private TurtleModel model;
  private String stampUrl;
  public static final double DEFAULT_ICON_SIZE = 30;
  public ImageView icon;
  private double iconSize;
  private Tooltip position;
  private final double TURTLE_SPEED = 100;

  public TurtleView(double posX, double posY, CanvasScreen screen, AppModel app){
    this.iconSize = DEFAULT_ICON_SIZE;
    this.model = new TurtleModel(posX, posY, screen.getBorderRectangle(), iconSize);
    this.stampUrl = app.getStampIconUrl();
    this.homeX = model.getPosX();
    this.homeY = model.getPosY();
    this.position = new Tooltip();
    this.icon = createIcon(model.getPosX(), model.getPosY(), iconSize, app.getTurtleIconUrl());
    installPositionLabel(icon, position, app);
  }
  public void move(double dist, CanvasScreen canvas, SequentialTransition animation){
    Line path = model.createTurtlePath(dist, canvas);
    path.setId(this.getIcon().getId());
    System.out.println(this.getIcon().getId());
    canvas.addLine(path);
    animation.getChildren().add(createPathAnimation(path, canvas));
  }
  public void rotateTurtle(int newAngle, SequentialTransition animation){
    RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
    rotate.setByAngle(newAngle);
    model.rotate(newAngle);
    animation.getChildren().add(rotate);
  }

  public void turnTurtle(int x, int y, SequentialTransition animation){
    RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
    double angle = -1 * Math.atan2(homeY - y - this.getModel().getPosY(),
                                    x + homeX - this.getModel().getPosX()) / Math.PI * 180;
    rotate.setByAngle(angle + model.getAngle());
    model.rotate(angle + model.getAngle());
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

  // TODO: Refactor to look better, unify with home method
  public void goTo(int posX, int posY, SequentialTransition animation){
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), icon);
    fadeOut.setFromValue(icon.getOpacity());
    fadeOut.setToValue(0.0);
    model.setPosition(posX + homeX, homeY - posY);
    fadeOut.setOnFinished(event -> {
      System.out.println("test");
      moveIcon(posX + homeX, homeY - posY);
      model.updateRelativePosition(icon, position);
    } );
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), icon);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(icon.getOpacity());
    animation.getChildren().add(fadeOut);
    animation.getChildren().add(fadeIn);
  }

  public void home(SequentialTransition animation){
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), icon);
    fadeOut.setFromValue(icon.getOpacity());
    fadeOut.setToValue(0.0);
    model.setPosition(homeX, homeY);
    fadeOut.setOnFinished(event -> {
      System.out.println("test");
      moveIcon(homeX, homeY);
      model.updateRelativePosition(icon, position);
    } );
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), icon);
    fadeIn.setFromValue(0.0);
    fadeIn.setToValue(icon.getOpacity());
    animation.getChildren().add(fadeOut);
    animation.getChildren().add(fadeIn);
  }

  /**
   *
   * @param canvas
   * @param animation
   */
  public void clear(CanvasScreen canvas, SequentialTransition animation){
    canvas.getLines().removeIf(i -> this.getIcon().getId().equals(i.getId())); // TODO: Fix this
    canvas.getShapes().getChildren().removeIf(i -> this.getIcon().getId().equals(i.getId()));
    this.home(animation);
  }
  public void stamp(CanvasScreen canvas, SequentialTransition animation){
    ImageView s = createIcon(model.getPosX(), model.getPosY(), iconSize, stampUrl);
    s.toFront();
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
  private ImageView createIcon(double x, double y, double size, String url){
    ImageView i = new ImageView(new Image(url));
    i.setFitHeight(size);
    i.setFitWidth(size);
    i.setX(x- size / 2);
    i.setY(y- size / 2);
    i.setRotate(-model.getAngle());
    i.toFront();
    return i;
  }
  public void installPositionLabel(ImageView i, Tooltip tooltip, AppModel app){
    i.setOnMouseEntered(event -> onHover());
    i.setOnMouseExited(event -> offHover());
    i.setOnMouseDragged(event -> onDrag(event, app));
    model.updateRelativePosition(i, tooltip);
    Tooltip.install(i, tooltip);
  }
  private void onDrag(MouseEvent e, AppModel app){
    double x = e.getX();
    double y = e.getY();
    if (!app.isRunning() && app.getMyCanvas().isClear() && x < model.getXMax() && x > model.getXMin() && y > model.getYMin() && y < model.getYMax()){
      moveIcon(x, y);
      model.setPosition(x, y);
      app.setHome(model.getRelX(), -model.getRelY());
      homeX = x;
      homeY = y;
    }
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
    pen.translateXProperty().addListener((ov, t, t1) -> moveIcon(pen.getTranslateX(), pen.getTranslateY()));
    pen.translateYProperty().addListener((ov, t, t1) -> moveIcon(pen.getTranslateX(), pen.getTranslateY()));
    PathTransition pathTransition = new PathTransition(Duration.seconds(distance / TURTLE_SPEED), line, pen);
    pathTransition.setOnFinished(event -> {
      if (show) canvas.getShapes().getChildren().add(1, line);
    });
    return pathTransition;
  }
  public void changeIcon(String s, AppModel app){
    app.getMyCanvas().getShapes().getChildren().remove(icon);
    icon = createIcon(model.getPosX(), model.getPosY(), iconSize, s);
    installPositionLabel(icon, position, app);
    app.getMyCanvas().getShapes().getChildren().add(icon);
  }
  public void changeStamp(String s) {
    stampUrl = s;
  }
  public TurtleModel getModel(){
    return model;
  }
}

