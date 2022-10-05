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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class CanvasScreen {
    private Rectangle borderRectangle;
    private Group shapes;
    private ResourceBundle myResources;
    private Color brushColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;
    private Double THICKNESS = 3.0;


    public CanvasScreen(ResourceBundle myResources) {
        this.myResources = myResources;
        shapes = new Group();
        borderRectangle = new Rectangle(300, 55, 500, 540);
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


    public WritableImage screenShot() {
        WritableImage snapshot = shapes.snapshot(null, null);
        return snapshot;
    }

    public void clear() {
        shapes.getChildren().removeIf(i -> !(i instanceof Rectangle));
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
    public boolean isClear(){
        if (shapes.getChildren().size() > 2){
            return false;
        }
        else return true;
    }
}