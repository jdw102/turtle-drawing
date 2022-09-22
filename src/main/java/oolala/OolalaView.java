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

public class OolalaView {
    private int SIZE_WIDTH;
    private int SIZE_HEIGHT;
    private Paint BACKGROUND;
    private BorderPane root;
    private Group paint;
    TextField textBox;

    public Scene setUpScene(int SIZE_WIDTH, int SIZE_HEIGHT) {
        this.SIZE_WIDTH = SIZE_WIDTH;
        this.SIZE_HEIGHT = SIZE_HEIGHT;
        root = new BorderPane();
        paint = new Group();
        root.getChildren().add(paint);
        root.setBottom(makeInputPanel());
        Scene scene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT);
        return scene;
    }

    private void clearPaint() {
        paint.getChildren().clear();
    }

    private Node makeInputPanel() {
        HBox result = new HBox();
        EventHandler<ActionEvent> textHandler = event -> showPaint(textBox.getText());
        EventHandler<ActionEvent> clearHandler = event -> clearPaint();
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

        drawLine(0, 0, 230, 0);

        return result;
    }

    private void showPaint(String text) {
        Text showText = new Text(30, 60, text);
        showText.setFont(new Font(15));
        showText.setFill(Color.RED);
        paint.getChildren().add(showText);
    }

    public void drawLine(int x, int y, int length, int direction) {
        Line line = new Line();
        line.setStartX(SIZE_WIDTH / 2.0 + x + x);
        line.setStartY(SIZE_HEIGHT / 2.0 + y + y);
        line.setEndX(SIZE_WIDTH / 2.0 + x + length * Math.cos(direction));
        line.setEndY(SIZE_HEIGHT / 2.0 + y + length * Math.sin(direction));
        paint.getChildren().add(line);
    }
}
