package oolala;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * @Author Luyao Wang
 * Setting up of the UI.
 * The origin is at the center of the screen.
 */
public class OolalaView {
    private int SIZE_WIDTH;
    private int SIZE_HEIGHT;
    private Paint BACKGROUND;
    private Paint BRUSH_COLOR;
    private BorderPane root;
    private Group canvas;
    private TextBox textBox;
    private ArrayList<Command> commands;
    private Turtle turtle;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private Parser parser = new Parser();
    private FileChooser fileChooser;
    private Stage stage;



    public Scene setUpScene(int SIZE_WIDTH, int SIZE_HEIGHT, String language, Stage stage) {
        this.stage = stage;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.SIZE_WIDTH = SIZE_WIDTH;
        this.SIZE_HEIGHT = SIZE_HEIGHT;
        root = new BorderPane();
        canvas = new Group();
        root.getChildren().add(canvas);
        makeTextBox();
        BorderPane.setAlignment(textBox.get(), Pos.CENTER);
        root.setPadding(new Insets(10, 30, 10, 10));
        root.setLeft(textBox.get());
        Scene scene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT);
        return scene;
    }

    /**
     * A method to remove everything on the canvas.
     *
     * @author Luyao Wang
     */
    private void makeTextBox(){
        textBox = new TextBox();
        EventHandler<ActionEvent> passCommands = event -> {
            ArrayList<Command> commands = parser.parse(textBox.getTextArea().getText());
            System.out.println(commands.toString());
        };
        fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("FileChooser"));
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            Path filePath = Path.of(f.getPath());
            try {
                String content = Files.readString(filePath);
                textBox.getTextArea().setText(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        textBox.getRunButton().setOnAction(passCommands);
        textBox.getFileChooserButton().setOnAction(openFileChooser);
    }
//    private Node makeInputPanel() {
//        HBox result = new HBox();
//        EventHandler<ActionEvent> textHandler = event -> {
//            commands = parser.parse(textBox.getText());
//            textBox.clear();
//            turtle.readInstruction(commands.get(0), this);
//        };
//        EventHandler<ActionEvent> clearHandler = event -> {
//            clearPaint();
//            turtle.resetTurtle(this);
//        };
//        textBox = new TextField();
//        textBox.setOnAction(textHandler);
//        textBox.setPrefWidth(390);
//        result.getChildren().add(textBox);
//        Button enterButton = new Button();
//        enterButton.setText("Draw");
//        enterButton.setOnAction(textHandler);
//        result.getChildren().add(enterButton);
//        Button clearButton = new Button();
//        clearButton.setText("Clear");
//        clearButton.setOnAction(clearHandler);
//        result.getChildren().add(clearButton);
//
////        drawLine(0, 0, 230, 0);
//
//        return result;
//    }

    private void clearPaint() {
        canvas.getChildren().clear();
    }

    private void showPaint(String text) {
        Text showText = new Text(30, 60, text);
        showText.setFont(new Font(15));
        showText.setFill(Color.RED);
        canvas.getChildren().add(showText);
    }

    /**
     * A method to draw a new line on the canvas.
     *
     * @param xStart         x coordinate of the start point
     * @param yStart         y coordinate of the end point
     * @param xEnd    x coordinate of end point
     * @param yEnd y coordinate of end point
     * @author Luyao Wang
     */

    public void drawLine(double xStart, double yStart, double xEnd, double yEnd, double thickness, Color color) {
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
        canvas.getChildren().add(0, line);
    }
    public Group getCanvas() {
        return canvas;
    }
}
