package oolala;

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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class CanvasScreen {
    private Rectangle borderRectangle;
    private Group shapes;
    private HBox hBox;
    private HBox stylingBox;
    private HBox settingsBox;
    private ResourceBundle myResources;
    private Color brushColor = Color.BLACK;
    private Color backgroundColor = Color.AZURE;
    private Double THICKNESS = 3.0;


    public CanvasScreen(ResourceBundle myResources) {
        this.myResources = myResources;
        shapes = new Group();

        borderRectangle = new Rectangle(300, 50, 500, 540);
        shapes.getChildren().add(borderRectangle);
        borderRectangle.setFill(backgroundColor);
    }

    public void setBrushColor(Color color) {
        brushColor = color;
    }

    public void setThickness(String value) {
        Alert alert = new Alert(Alert.AlertType.ERROR, myResources.getString("NumberFormatException"));
        try {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue > 0) THICKNESS = doubleValue;
            else alert.showAndWait();
        } catch (NumberFormatException e) {
            alert.showAndWait();
        }
    }

    private void changeColor(Color clr) {
        brushColor = clr;
    }

    public WritableImage screenShot() {
        WritableImage snapshot = shapes.snapshot(null, null);
        return snapshot;
    }

    public void clear() {
        //same way of implementing
//        for (Node i : shapes.getChildren()) {
//            if (i instanceof Line)
//                shapes.getChildren().remove(i);
//        }
        shapes.getChildren().removeIf(i -> i instanceof Line);
    }

    public Group getShapes() {
        return shapes;
    }

    public Rectangle getBorderRectangle() {
        return borderRectangle;
    }

    public Double getThickness() {
        return THICKNESS;
    }

    public Color getBrushColor() {
        return brushColor;
    }
}
