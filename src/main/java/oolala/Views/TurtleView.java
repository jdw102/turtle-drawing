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
import oolala.Models.AppModel;
import oolala.Models.Position;
import oolala.Models.RunningStatus;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Models.TurtleModel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A view class to display the turtle, communicate with the turtle model, and adjust according to commands.
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
    private Tooltip tooltip;
    private final double TURTLE_SPEED = 100;
    private Group shapes;
    private RunningStatus runningStatus;
    private SequentialTransition animation;
    private CanvasScreen canvasScreen;

    public TurtleView(double posX, double posY, CanvasScreen screen, String stampUrl, String turtleIconUrl, RunningStatus runningStatus, SequentialTransition animation, AppModel app) {
        this.runningStatus = runningStatus;
        canvasScreen = screen;
        this.animation = animation;
        shapes = canvasScreen.getShapes();
        iconSize = DEFAULT_ICON_SIZE;
        model = new TurtleModel(posX, posY, screen.getBorderRectangle(), iconSize);
        this.stampUrl = stampUrl;
        homeX = model.getPos().getX();
        homeY = model.getPos().getY();
        tooltip = new Tooltip();
        this.icon = createIcon(model.getPos().getX(), model.getPos().getY(), iconSize, turtleIconUrl);
        installPositionLabel(icon, tooltip, app);
    }
    /**
     * A method to create a line to be added to the canvas.
     *
     * @param xStart - Starting x coordinate of line.
     * @param yStart - Starting y coordinate of line.
     * @param xEnd - Ending x coordinate of line.
     * @param yEnd - Ending y coordinate of line.
     * @return A Line of specified parameters.
     * @author Jerry Worthy
     */
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
    /**
     * A method to be called to move the turtle icon a certain distance, positive or negative, update the model,
     * and add the line to the canvas screen.
     *
     * @param dist - The distance to travel.
     * @author Jerry Worthy
     */
    public void move(double dist) {
        Position oldPos = model.getPos();
        Position newPos = model.calculateMove(dist);
        Line path = createLine(oldPos.getX(), oldPos.getY(), newPos.getX(), newPos.getY());
        path.setId("Line" + Integer.toString(canvasScreen.getLines().size() + 1));
        canvasScreen.addLine(path);
        animation.getChildren().add(createPathAnimation(path));
    }
    /**
     * A method to create a rotation animation of the icon and add it to the final animation and update the angle of the model.
     *
     * @param newAngle - The angle by which to rotate the icon/update the model.
     * @author Jerry Worthy
     */
    public void rotateTurtle(int newAngle) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), icon);
        rotate.setByAngle(newAngle);
        model.rotate(newAngle);
        model.setTooltipRelativePosition(icon, tooltip);
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
            model.setTooltipRelativePosition(icon, tooltip);
            model.updateRelativePosition();
        });
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), icon);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(icon.getOpacity());
        animation.getChildren().add(fadeOut);
        animation.getChildren().add(fadeIn);
    }

    public void stamp() {
        ImageView s = createIcon(model.getPos().getX(), model.getPos().getY(), iconSize, stampUrl);
        s.toFront();
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
        model.updateRelativePosition();
        model.setTooltipRelativePosition(icon, tooltip);
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

    public void installPositionLabel(ImageView i, Tooltip tooltip, AppModel app) {
        i.setOnMouseEntered(event -> onHover());
        i.setOnMouseExited(event -> offHover());
        i.setOnMouseDragged(event -> onDrag(event, runningStatus, app));
        model.setTooltipRelativePosition(i, tooltip);
        Tooltip.install(i, tooltip);
    }

    private void onDrag(MouseEvent e, RunningStatus runningStatus, AppModel app) {
        double x = e.getX();
        double y = e.getY();
        if (!runningStatus.getRunningStatus() && app.getMyCanvas().isClear() && model.getAngle() % 360 == 0 && x < model.getXMax() && x > model.getXMin() && y > model.getYMin() && y < model.getYMax()) {
            changeHome(x, y, app);
        }
    }

    public void changeHome(double x, double y, AppModel app) {
        moveIcon(x, y);
        model.setPosition(x, y);
        app.setHome(model.getRelPos().getX(), -model.getRelPos().getY());
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

    public void changeIcon(String s, AppModel app) {
        shapes.getChildren().remove(icon);
        icon = createIcon(model.getPos().getX(), model.getPos().getY(), iconSize, s);
        installPositionLabel(icon, tooltip, app);
        shapes.getChildren().add(icon);
    }

    public void changeStamp(String s) {
        stampUrl = s;
    }

    public TurtleModel getModel() {
        return model;
    }
}

