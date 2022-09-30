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

        //Area indicator
//        vBox.getChildren().add(Buttons);
//        borderRectangle = new Rectangle();
//        vBox.getChildren().add(borderRectangle);
//        borderRectangle.widthProperty().bind(vBox.widthProperty().subtract(60));
//        borderRectangle.heightProperty().bind(vBox.heightProperty().subtract(60));
//        borderRectangle.translateXProperty().bind((vBox.widthProperty().divide(2)).subtract((borderRectangle.widthProperty().divide(2))));
//        borderRectangle.translateYProperty().bind((vBox.heightProperty().divide(2)).subtract((borderRectangle.heightProperty().divide(2))));

        //vBox.getChildren().add(hBox);
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

    public void screenShot() {
        WritableImage snapshot = shapes.snapshot(null, null);
        File file = new File("snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    /**
     * A method to draw a new line on the canvas.
     *
     * @param xStart x coordinate of the start point
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @author Luyao Wang
     */
    public void drawLine(double xStart, double yStart, double xEnd, double yEnd) {
        System.out.println(xStart);
        System.out.println(yStart);
        System.out.println(xEnd);
        System.out.println(yEnd);
        Line line = new Line();
        line.setStartX(xStart);
        line.setStartY(yStart);
        line.setEndX(xEnd);
        line.setEndY(yEnd);
        line.setStrokeWidth(THICKNESS);
        line.setStroke(brushColor);
        //TODO: is color an attribute of a turtle?
        shapes.getChildren().add(1, line);
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

    public TextField makeTextField(String property, String defaultValue, EventHandler<ActionEvent> handler) {
        TextField textField = new TextField(property);
        textField.setText(defaultValue);
        textField.setOnAction(handler);
        return textField;
    }

    public ComboBox<String> makeComboBoxArrayList(ArrayList<String> items, EventHandler<ActionEvent> handler) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items.get(0));//Default language
        comboBox.setOnAction(handler);
        return comboBox;
    }

    public ColorPicker makeColorPicker(EventHandler<ActionEvent> handler, Color defaultColor, String tooltip) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(defaultColor);
        colorPicker.setOnAction(handler);
        Tooltip.install(colorPicker, new Tooltip(myResources.getString(tooltip)));
        return colorPicker;
    }

    public Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }
    public Rectangle getBorderRectangle() {
        return borderRectangle;
    }
}
