package oolala.Views.ViewComponents;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import oolala.Views.TurtleView;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * A class to contain the rectangle to act as the canvas and a group of shapes that the turtle will add lines and stamps
 * to when it moves. It also contains the color and thickness of the lines.
 *
 * @author Luyao Wang
 */
public class CanvasScreen {
    private Rectangle borderRectangle;
    private Group shapes;
    private ResourceBundle myResources;
    private Color brushColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;
    private Double thickness = 3.0;
    private final int START_X = 320;
    private final int START_Y = 56;
    private final int WIDTH = 510;
    private final int HEIGHT = 556;
    private List<Line> lines;


    public CanvasScreen(ResourceBundle myResources) {
        this.myResources = myResources;
        shapes = new Group();
        shapes.setId("CanvasShapes");
        borderRectangle = new Rectangle(START_X, START_Y, WIDTH, HEIGHT);
        borderRectangle.setId("BorderRectangle");
        shapes.getChildren().add(borderRectangle);
        borderRectangle.setFill(backgroundColor);
        borderRectangle.setArcHeight(10);
        borderRectangle.setArcWidth(10);
        lines = new ArrayList<>();
    }

    public void setBrushColor(Color color) {
        brushColor = color;
    }
    /**
     * A method to change the thickness of the line drawn, if it is not a valid number an alert is displayed.
     *
     * @param value - The new thickness value.
     * @author Luyao Wang
     */
    public void setThickness(String value) {
        Alert alert = new Alert(Alert.AlertType.ERROR, myResources.getString("NumberFormatException"));
        try {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue > 0) thickness = doubleValue;
            else alert.showAndWait();
        } catch (NumberFormatException e) {
            alert.showAndWait();
        }
    }

    /**
     * A method to create writable image to be saved in the view.
     *
     * @return A writeable image.
     * @author Luyao Wang
     */
    public WritableImage screenShot() {
        WritableImage snapshot = shapes.snapshot(null, null);
        return snapshot;
    }
    /**
     * A method to clear all shapes except for the rectangle.
     *
     * @author Jerry Worthy
     */
    public void clear() {
        shapes.getChildren().removeIf(i -> i instanceof ImageView);
        shapes.getChildren().removeAll(lines);
    }

    public Group getShapes() {
        return shapes;
    }
    public Rectangle getBorderRectangle() {
        return borderRectangle;
    }
    public Double getThickness() {
        return thickness;
    }
    public Color getBrushColor() {
        return brushColor;
    }
    public boolean isClear(){
        return !(shapes.getChildren().size() > 2);
    }
    public void addLine(Line line){
        lines.add(line);
    }
    public List<Line> getLines(){
        return lines;
    }
}
