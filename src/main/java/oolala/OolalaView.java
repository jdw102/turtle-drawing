package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
    private TextBox textBox;
    private ArrayList<Command> commands;
    private Turtle turtle;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private Parser parser = new Parser();
    private FileChooser fileChooser;
    private Stage stage;

    private CanvasScreen canvasScreen;
    private VBox canvasVBox;
    private Group canvasShapes;
    private static final String DEFAULT_LANGUAGE = "English";
    private String language;


    public Scene setUpScene(int SIZE_WIDTH, int SIZE_HEIGHT, Stage stage) {
        this.stage = stage;
        this.language = DEFAULT_LANGUAGE;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.SIZE_WIDTH = SIZE_WIDTH;
        this.SIZE_HEIGHT = SIZE_HEIGHT;

        root = new BorderPane();

        makeTextBox();
        root.setLeft(textBox.get());

        makeCanvasScene();
        root.setCenter(canvasVBox);
        root.getChildren().add(canvasShapes);

        root.setPadding(new Insets(10, 10, 10, 10));
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
        System.out.println(language);
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
            textBox.updateRecentlyUsed();
            ArrayList<Command> commands = parser.parse(textBox.getTextArea().getText());
            canvasScreen.setCommands(commands, this);
        };
        fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("FileChooser"));
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null){
                try {
                    Path filePath = Path.of(f.getPath());
                    String content = Files.readString(filePath);
                    textBox.getTextArea().setText(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        EventHandler<ActionEvent> saveFile = event -> {
            File f = fileChooser.showSaveDialog(stage);
            if (f != null) {
                try {
                    textBox.updateRecentlyUsed();
                    FileWriter writer = new FileWriter(f);
                    writer.write(textBox.getTextArea().getText());
                    writer.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        textBox.getRunButton().setOnAction(passCommands);
        textBox.getFileChooserButton().setOnAction(openFileChooser);
        textBox.getSaveButton().setOnAction(saveFile);
    }

}
