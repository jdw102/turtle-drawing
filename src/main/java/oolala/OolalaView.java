package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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
    private TextBox textBox;
    private ArrayList<Command> commands;
    private Turtle turtle;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private Parser parser = new Parser();
    private CanvasScreen canvasScreen;
    private VBox canvasVBox;
    private Group canvasShapes;
    private String language = "English";

    public Scene setUpScene(int SIZE_WIDTH, int SIZE_HEIGHT) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.SIZE_WIDTH = SIZE_WIDTH;
        this.SIZE_HEIGHT = SIZE_HEIGHT;

        root = new BorderPane();

        makeTextBox();
        //BorderPane.setAlignment(textBox.get(), Pos.CENTER);
        // TODO: can the above commented line be deleted?
        root.setLeft(textBox.get());

        makeCanvasScene();
        root.setCenter(canvasVBox);
        root.getChildren().add(canvasShapes);

        root.setPadding(new Insets(10, 30, 10, 10));


//        VBox box = new VBox();
//        root.setRight(box);
//        box.setPrefWidth(400);
//        System.out.println(box.getLayoutX());
//        System.out.println(box.getLayoutY());
//        BorderPane.setAlignment(box, Pos.CENTER);
//        String cssLayout = "-fx-border-color: red;\n" +
//                "-fx-border-insets: 5;\n" +
//                "-fx-border-width: 3;\n" +
//                "-fx-border-style: solid;\n";
//        box.setStyle(cssLayout);
        //        root.setRight(box);


//        turtle = new Turtle();
//        root.getChildren().add(turtle.getIcon());
        Scene scene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT);
        return scene;
    }

    public CanvasScreen getCanvasScreen() {
        return canvasScreen;
    }


    /**
     * A method to remove everything on the canvas.
     *
     * @author Luyao Wang
     */


    private void setLanguage(String lang) {
        language = lang;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        canvasScreen.setLanguage(myResources);
        textBox.setLanguage(myResources);
    }

    private void makeCanvasScene() {
        canvasScreen = new CanvasScreen(myResources);
        canvasVBox = canvasScreen.getVBox();
        canvasShapes = canvasScreen.getShapes();

        ComboBox<String> languages = canvasScreen.getLanguagesComboBox();

        EventHandler<ActionEvent> langCommand = event -> {
            setLanguage(languages.getValue());
        };
        languages.setOnAction(langCommand);
    }

    private void makeTextBox() {
        textBox = new TextBox(myResources);
        EventHandler<ActionEvent> passCommands = event -> {
            ArrayList<Command> commands = parser.parse(textBox.getTextArea().getText());
            canvasScreen.setCommands(commands, this);
            //canvasScreen.getTurtle().readInstruction(commands.get(0), this);
        };
        textBox.getRunButton().setOnAction(passCommands);
    }
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

