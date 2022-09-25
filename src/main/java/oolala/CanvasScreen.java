package oolala;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CanvasScreen {

    //ArrayList
    private Canvas canvas;
    private Rectangle borderRectangle;
    VBox vBox;
    Group shapes;
    private GraphicsContext gc;
    Turtle turtle;

    public CanvasScreen() {

        shapes = new Group();
        vBox = new VBox();
//        vBox.setPrefHeight(600);
//        vBox.setPrefWidth(500);
        vBox.setMinSize(400, 500);


        Button clearButton = makeButtons("clear");
        Button resetButton = makeButtons("reset");
        Button saveButton = makeButtons("save");
        EventHandler<ActionEvent> clearCommand = event -> clear();
        clearButton.setOnAction(clearCommand);
        EventHandler<ActionEvent> resetCommand = event -> reset();
        resetButton.setOnAction(resetCommand);
        EventHandler<ActionEvent> saveCommand = event -> screenShot();
        saveButton.setOnAction(saveCommand);

        HBox Buttons = new HBox(clearButton, resetButton, saveButton);
        Buttons.setAlignment(Pos.TOP_RIGHT);


        //Area indicator
//        vBox.getChildren().add(Buttons);
//        borderRectangle = new Rectangle();
//        vBox.getChildren().add(borderRectangle);
//        borderRectangle.widthProperty().bind(vBox.widthProperty().subtract(60));
//        borderRectangle.heightProperty().bind(vBox.heightProperty().subtract(60));
//        borderRectangle.translateXProperty().bind((vBox.widthProperty().divide(2)).subtract((borderRectangle.widthProperty().divide(2))));
//        borderRectangle.translateYProperty().bind((vBox.heightProperty().divide(2)).subtract((borderRectangle.heightProperty().divide(2))));

        vBox.getChildren().add(Buttons);
        borderRectangle = new Rectangle(260, 50, 500, 500);
        shapes.getChildren().add(borderRectangle);
        borderRectangle.setFill(Color.AZURE);

        turtle = new Turtle(0, 0, 0, borderRectangle);
        System.out.println(borderRectangle.heightProperty());
        shapes.getChildren().add(turtle.getIcon());
    }

    public void setCommands(ArrayList<Command> commands, OolalaView display) {
        Iterator<Command> itCmd = commands.iterator();
        while (itCmd.hasNext()) {
            Command instruction = itCmd.next();
            turtle.readInstruction(instruction, display);
            itCmd.remove();
        }
    }

    private void screenShot() {
        WritableImage snapshot = shapes.snapshot(null, null);
        File file = new File("snapshot");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    public Turtle getTurtle() {
        return turtle;
    }

    /**
     * A method to draw a new line on the canvas.
     *
     * @param xStart    x coordinate of the start point
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @param thickness
     * @param color
     * @author Luyao Wang
     */
    public void drawLine(double xStart, double yStart, double xEnd, double yEnd, double thickness, Color color) {
        System.out.println(xStart);
        System.out.println(yStart);
        System.out.println(xEnd);
        System.out.println(yEnd);
        Line line = new Line();
//        line.setStartX(SIZE_WIDTH / 2.0 + xStart);
//        line.setStartY(SIZE_HEIGHT / 2.0 - yStart);
        line.setStartX(xStart);
        line.setStartY(yStart);
//        line.setEndX(SIZE_WIDTH / 2.0 + x + length * Math.cos(Math.toRadians(direction)));
//        line.setEndY(SIZE_HEIGHT / 2.0 - y - length * Math.sin(Math.toRadians(direction)));
        line.setEndX(xEnd);
        line.setEndY(yEnd);
        line.setStrokeWidth(thickness);
        line.setFill(color);
        shapes.getChildren().add(line);
    }

    public void reset() {
        turtle.home();
    }

    public void clear() {
        //same way of implementing
//        for (Node i : shapes.getChildren()) {
//            if (i instanceof Line)
//                shapes.getChildren().remove(i);
//        }
        shapes.getChildren().removeIf(i -> i instanceof Line);
    }

    public VBox getVBox() {
        return vBox;
    }

    public Group getShapes() {
        return shapes;
    }

    private Button makeButtons(String property) {
        Button button = new Button(property);
        return button;
    }
}
