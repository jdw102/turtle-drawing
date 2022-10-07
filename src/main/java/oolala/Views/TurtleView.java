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

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A view class to display the turtle, communicate with the turtle model, and adjust according to commands.
 *
 * @author Jerry Worthy
 */
public class TurtleView {
    private double homeX;
    private double homeY;
    private TurtleModel model;
    private String stampUrl;
    private String iconUrl;
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
        homeX = model.getPos().posX;
        homeY = model.getPos().posY;
        tooltip = new Tooltip();
        this.icon = createIcon(model.getPos().posX, model.getPos().posY, iconSize, turtleIconUrl);
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
     * A method to be called to move the turtle icon a certain distance using a path transition, positive or negative, update the model,
     * and add the line to the canvas screen.
     *
     * @param dist - The distance to travel.
     * @author Jerry Worthy
     */
    public void move(double dist) {
        Position oldPos = model.getPos();
        Position newPos = model.calculateMove(dist);
        Line path = createLine(oldPos.posX, oldPos.posY, newPos.posX, newPos.posY);
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
    /**
     * A method to create a fade transition that fades the turtle in.
     *
     * @author Jerry Worthy
     */
    public void showTurtle() {
        model.setShown(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
        fade.setFromValue(icon.getOpacity());
        fade.setToValue(1.0);
        fade.setOnFinished(event -> icon.toFront());
        animation.getChildren().add(fade);
    }
    /**
     * A method to create a fade transition that fades the turtle out.
     *
     * @author Jerry Worthy
     */
    public void hideTurtle() {
        model.setShown(false);
        FadeTransition fade = new FadeTransition(Duration.seconds(0.25), icon);
        fade.setFromValue(icon.getOpacity());
        fade.setToValue(0.0);
        fade.setOnFinished(event -> icon.toBack());
        animation.getChildren().add(fade);
    }
    /**
     * A method to move the turtle icon back to the home position and reset the model location to the home location,
     * it creates a fade out and fade in transition.
     *
     * @author Jerry Worthy
     */
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
    /**
     * A method to add a copy of the icon to the canvas and to fade it in.
     *
     * @author Jerry Worthy
     */
    public void stamp() {
        ImageView s = createIcon(model.getPos().posX, model.getPos().posY, iconSize, stampUrl);
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
    /**
     * A method to move the icon to a set location, and to update the relative position in the model and update
     * the position displayed by the tooltip.
     *
     * @param x - The x coordinate.
     * @param y - The y coordinate.
     * @author Jerry Worthy
     */
    private void moveIcon(double x, double y) {
        this.icon.setX(x - iconSize / 2);
        this.icon.setY(y - iconSize / 2);
        this.icon.toFront();
        model.updateRelativePosition();
        model.setTooltipRelativePosition(icon, tooltip);
    }
    /**
     * A method to create the image view that will act as the turtle icon.
     *
     * @param x - The x coordinate.
     * @param y - The y coordinate.
     * @param size - The size of the image view.
     * @param url - The url for the image.
     * @author Jerry Worthy
     */
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
    /**
     * A method to assign the image view the events that allow it to be more interactive by installing the
     * tooltip, making it larger on hover, and allowing the icon to be dragged to a new home location.
     *
     * @param i - The image view to be changed.
     * @param tooltip - The tooltip to attach to the image view.
     * @param app - The app model to be changed by the on drag event (changing its home x and home y).
     * @author Jerry Worthy
     */
    public void installPositionLabel(ImageView i, Tooltip tooltip, AppModel app) {
        i.setOnMouseEntered(event -> onHover());
        i.setOnMouseExited(event -> offHover());
        i.setOnMouseDragged(event -> onDrag(event, app));
        model.setTooltipRelativePosition(i, tooltip);
        Tooltip.install(i, tooltip);
    }
    /**
     * A method to move the icon and change the home location when the icon is dragged.
     *
     * @param e - The mouse event.
     * @param app - The app model.
     * @author Jerry Worthy
     */
    private void onDrag(MouseEvent e, AppModel app) {
        double x = e.getX();
        double y = e.getY();
        if (!runningStatus.isRunning() && app.getMyCanvas().isClear() && model.getAngle() % 360 == 0 && x < model.getXMax() && x > model.getXMin() && y > model.getYMin() && y < model.getYMax()) {
            changeHome(x, y, app);
        }
    }
    /**
     * A method to change the home location of the app model, the view, and update the icon and model location appropriately.
     * It is called by the onDrag event of the icon.
     *
     * @param x - The x coordinate to be set as home x.
     * @param y - The y coordinate to be set as home y.
     * @param app - The app to edit.
     * @author Jerry Worthy
     */
    public void changeHome(double x, double y, AppModel app) {
        moveIcon(x, y);
        model.setPosition(x, y);
        app.setHome(model.getRelPos().posX, -model.getRelPos().posY);
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
    /**
     * A method to move create a path transition that moves the icon along the generated line.
     *
     * @param line - The line to follow during the path transition.
     * @author Jerry Worthy
     */
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
    /**
     * A method to change the turtle icon by removing the old one and creating the new one with the new image.
     *
     * @param s - The new image url.
     * @param app - The app model.
     * @author Jerry Worthy
     */
    public void changeIcon(String s, AppModel app) {
        iconUrl = s;
        shapes.getChildren().remove(icon);
        icon = createIcon(model.getPos().posX, model.getPos().posY, iconSize, iconUrl);
        installPositionLabel(icon, tooltip, app);
        shapes.getChildren().add(icon);
    }

    public void changeStamp(String s) {
        stampUrl = s;
    }

    public String getStampUrl() {
        return stampUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public TurtleModel getModel() {
        return model;
    }
}

