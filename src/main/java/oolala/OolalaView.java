package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


/**
 * @Author Luyao Wang
 * Setting up of the UI.
 * The origin is at the center of the screen.
 */
public class OolalaView {
    private int SIZE_WIDTH;
    private int SIZE_HEIGHT;
    private Paint BACKGROUND;
    private BorderPane root;
    private Group canvas;
    TextField textBox;
    private Parser parser = new Parser();
    private ArrayList<Command> commands;
    private Turtle turtle;

    public Scene setUpScene(int SIZE_WIDTH, int SIZE_HEIGHT) {
        this.SIZE_WIDTH = SIZE_WIDTH;
        this.SIZE_HEIGHT = SIZE_HEIGHT;
        root = new BorderPane();
        canvas = new Group();
        root.getChildren().add(canvas);
        root.setBottom(makeInputPanel());
        turtle = new Turtle();
        root.getChildren().add(turtle.getIcon());
        Scene scene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT);
        return scene;
    }

    /**
     * A method to remove everything on the canvas.
     *
     * @author Luyao Wang
     */
    private Node makeInputPanel() {
        HBox result = new HBox();
        EventHandler<ActionEvent> textHandler = event -> {
            commands = parser.parse(textBox.getText());
            textBox.clear();
            System.out.println(commands.get(0).prefix.toString());
            turtle.readInstruction(commands.get(0), this);
        };
        EventHandler<ActionEvent> clearHandler = event -> {
            clearPaint();
            turtle.resetTurtle();
        };
        textBox = new TextField();
        textBox.setOnAction(textHandler);
        textBox.setPrefWidth(390);
        result.getChildren().add(textBox);
        Button enterButton = new Button();
        enterButton.setText("Draw");
        enterButton.setOnAction(textHandler);
        result.getChildren().add(enterButton);
        Button clearButton = new Button();
        clearButton.setText("Clear");
        clearButton.setOnAction(clearHandler);
        result.getChildren().add(clearButton);

//        drawLine(0, 0, 230, 0);

        return result;
    }

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
     * @param x         x coordinate of the start point
     * @param y         y coordinate of the end point
     * @param length    length of the line
     * @param direction angle between the line and the positive x-axis
     * @author Luyao Wang
     */

    public void drawLine(double x, double y, int length, int direction, double thickness) {
        Line line = new Line();
//        line.setStartX(SIZE_WIDTH / 2.0 + x + x);
//        line.setStartY(SIZE_HEIGHT / 2.0 + y + y);
        line.setStartX(x);
        line.setStartY(y);
//        line.setEndX(SIZE_WIDTH / 2.0 + x + length * Math.cos(direction));
//        line.setEndY(SIZE_HEIGHT / 2.0 + y + length * Math.sin(direction));
        line.setEndX(x + length * Math.cos(Math.toRadians(direction)));
        line.setEndY(y - length * Math.sin(Math.toRadians(direction)));
        line.setStrokeWidth(thickness);
        canvas.getChildren().add(line);
    }
}
