package oolala.Views;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.util.Duration;
import oolala.Models.Position;
import oolala.Models.RunningStatus;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Models.TurtleModel;

import java.util.ArrayList;
import java.util.Collection;

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
    public ArrayList<ImageView> stamps;
    private double iconSize;
    private Tooltip position;
    private final double TURTLE_SPEED = 100;
    private Group shapes;
    private RunningStatus runningStatus;
    private SequentialTransition animation;
    private CanvasScreen canvasScreen;
    public TurtleView(double posX, double posY, CanvasScreen screen, String stampUrl, String turtleIconUrl, RunningStatus runningStatus, SequentialTransition animation) {
        this.runningStatus = runningStatus;
        canvasScreen = screen;
        this.animation = animation;
        this.shapes = screen.getShapes();
        this.iconSize = DEFAULT_ICON_SIZE;
        this.model = new TurtleModel(posX, posY, screen.getBorderRectangle(), iconSize);
        this.stampUrl = stampUrl;
        this.homeX = model.getPos().PosX;
        this.homeY = model.getPos().PosY;
        this.position = new Tooltip();
        this.icon = createIcon(model.getPos().PosX, model.getPos().PosY, iconSize, turtleIconUrl);
        installPositionLabel(icon, position);
        this.stamps = new ArrayList<>();
    }

    private Line createLine(double xStart, double yStart, double xEnd, double yEnd) {
        Line line = new Line();
        line.setStartX(xStart);
        line.setStartY(yStart);
        line.setEndX(xEnd);
        line.setEndY(yEnd);
        line.setStrokeWidth(canvasScreen.getThickness());
        line.setStroke(canvasScreen.getBrushColor());
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        return line;
    }

    public void move(double dist) {
        Position oldPos = model.getPos();
        Position newPos =  model.calculateMove(dist);
        Line line = createLine(oldPos.PosX, oldPos.PosY, newPos.PosX, newPos.PosY);
        animation.getChildren().add(createPathAnimation(line));
    }

    public void rotateTurtle(int newAngle) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
        rotate.setByAngle(newAngle);
        model.rotate(newAngle);
        animation.getChildren().add(rotate);
    }

    public void showTurtle() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
        fade.setFromValue(icon.getOpacity());
        fade.setToValue(1.0);
        fade.setOnFinished(event -> icon.toFront());
        animation.getChildren().add(fade);
    }

    public void hideTurtle() {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
        fade.setFromValue(icon.getOpacity());
        fade.setToValue(0.0);
        fade.setOnFinished(event -> icon.toBack());
        animation.getChildren().add(fade);
    }

    public void home() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), icon);
        fadeOut.setFromValue(icon.getOpacity());
        fadeOut.setToValue(0.0);
        model.setPosition(homeX, homeY);
        fadeOut.setOnFinished(event -> {
            System.out.println("test");
            moveIcon(homeX, homeY);
            model.updateRelativePosition(icon, position);
        });
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), icon);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(icon.getOpacity());
        animation.getChildren().add(fadeOut);
        animation.getChildren().add(fadeIn);
    }

    public void stamp() {
        ImageView s = createIcon(model.getPos().PosX, model.getPos().PosY, iconSize, stampUrl);
        s.toFront();
        stamps.add(s);
        s.setOpacity(0.0);
        canvasScreen.getShapes().getChildren().add(s);
        FadeTransition fade = new FadeTransition(Duration.seconds(0.25), s);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        animation.getChildren().add(fade);
    }

    public Node getIcon() {
        return icon;
    }

    private void moveIcon(double x, double y) {
        this.icon.setX(x - iconSize / 2);
        this.icon.setY(y - iconSize / 2);
        this.icon.toFront();
        model.updateRelativePosition(icon, position);
    }

    private ImageView createIcon(double x, double y, double size, String url) {
        ImageView i = new ImageView(new Image(url));
        i.setFitHeight(size);
        i.setFitWidth(size);
        i.setX(x - size / 2);
        i.setY(y - size / 2);
        i.setRotate(-model.getAngle());
        i.toFront();
        return i;
    }

    public void installPositionLabel(ImageView i, Tooltip tooltip) {
        i.setOnMouseEntered(event -> onHover());
        i.setOnMouseExited(event -> offHover());
        i.setOnMouseDragged(event -> onDrag(event, runningStatus));
        model.updateRelativePosition(i, tooltip);
        Tooltip.install(i, tooltip);
    }

    private void onDrag(MouseEvent e, RunningStatus runningStatus) {
        double x = e.getX();
        double y = e.getY();
        if (!runningStatus.getRunningStatus()) moveTurtle(x, y);
    }

    public void moveTurtle(double x, double y) {
        moveIcon(x, y);
        model.setPosition(x, y);
        //app.setHome(model.getRelX(), -model.getRelY());
        //TODO: Restore sethome functionality
        homeX = x;
        homeY = y;
    }

    private void onHover() {
        icon.setScaleX(1.1);
        icon.setScaleY(1.1);
    }

    private void offHover() {
        icon.setScaleX(1 / 1.1);
        icon.setScaleY(1 / 1.1);
    }

    public void putPenUp() {
        model.putPenUp();
    }

    public void putPenDown() {
        model.putPenDown();
    }

    private PathTransition createPathAnimation(Line line) {
        double distance = Math.sqrt(Math.pow((line.getEndX() - line.getStartX()), 2) + Math.pow((line.getEndY() - line.getStartY()), 2));
        Circle pen = new Circle(canvasScreen.getThickness() / 2);
        pen.setFill(canvasScreen.getBrushColor());
        Collection<Circle> penPoints = new ArrayList<>();
        boolean show = model.isPenDown();
        pen.translateXProperty().addListener((ov, t, t1) -> moveIcon(pen.getTranslateX(), pen.getTranslateY()));
        pen.translateYProperty().addListener((ov, t, t1) -> moveIcon(pen.getTranslateX(), pen.getTranslateY()));
        PathTransition pathTransition = new PathTransition(Duration.seconds(distance / TURTLE_SPEED), line, pen);
        pathTransition.setOnFinished(event -> {
            if (show) canvasScreen.getShapes().getChildren().add(1, line);
        });
        return pathTransition;
    }

    public void changeIcon(String s) {
        shapes.getChildren().remove(icon);
        icon = createIcon(model.getPos().PosX, model.getPos().PosY, iconSize, s);
        installPositionLabel(icon, position);
        shapes.getChildren().add(icon);
    }

    public void changeStamp(String s) {
        stampUrl = s;
    }
}

