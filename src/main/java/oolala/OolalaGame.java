package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static oolala.Command.CmdName.TELL;

/**
 * @Author Luyao Wang
 * Setting up of the UI.
 * The origin is at the center of the screen.
 */
public class OolalaGame {
    private int SIZE_WIDTH;
    private int SIZE_HEIGHT;
    private int textBoxWidth = 275;
    private int textBoxHeight = 600;
    private String historyText = "Command History";
    private BorderPane root;
    private TextBox textBox;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private Parser parser;
    private FileChooser fileChooser;
    private Stage stage;
    private CanvasScreen canvasScreen;
    private VBox textBoxVBox;
    private HBox textBoxButtonsHBox;
    private HBox canvasHBox;
    private ListView<String> recentlyUsed;
    private TextArea textArea;
    private Label historyLabel;
    private Group canvasShapes;
    private static final String DEFAULT_LANGUAGE = "English";
    private String language;
    private ArrayList<String> languages = new ArrayList<String>(Arrays.asList("English", "简体中文", "繁體中文", "日本語"));
    private ArrayList<String> canvasButtonsLabels = new ArrayList<String>(Arrays.asList("ClearCanvasButton", "ResetTurtleButton", "SaveButton"));
    private ArrayList<String> textBoxButtonsLabels = new ArrayList<String>(Arrays.asList("ImportButton", "SaveButton", "RunButton", "ClearTextButton"));

    private ComboBox<String> languagesComboBox;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    private Color brushColor;
    private Color backgroundColor;
    private HashMap<Integer, Turtle> turtles;
    private ArrayList<Integer> currTurtleIdxs;

    public Scene setUpScene(int SIZE_WIDTH, int SIZE_HEIGHT, Stage stage) {
        this.stage = stage;
        this.language = DEFAULT_LANGUAGE;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        parser = new Parser(myResources);
        this.SIZE_WIDTH = SIZE_WIDTH;
        this.SIZE_HEIGHT = SIZE_HEIGHT;

        root = new BorderPane();

        textBox = new TextBox(myResources);
        textBoxVBox = makeTextBoxVBox();
        root.setLeft(textBoxVBox);

        canvasScreen = new CanvasScreen(myResources);
        canvasHBox = makeCanvasHBox();
        canvasShapes = canvasScreen.getShapes();
        root.setCenter(canvasHBox);
        root.getChildren().add(canvasShapes);


        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        turtles.put(1, new Turtle(1, 0, 0, canvasScreen));
        currTurtleIdxs.add(1);
        canvasScreen.getShapes().getChildren().add(turtles.get(1).getIcon());


        root.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(root, SIZE_WIDTH, SIZE_HEIGHT);
        return scene;
    }

    public VBox makeTextBoxVBox() {
        VBox vBox = new VBox();
        textArea = textBox.makeTextArea();
        textBoxButtonsHBox = makeTextButtonsHBox();


        EventHandler<MouseEvent> addLine = event -> {
            textBox.addLine(recentlyUsed.getSelectionModel().getSelectedItem(), textArea);
        };
        recentlyUsed = textBox.makeListView(200, addLine);

        historyLabel = new Label(historyText);
        HBox historyTitle = new HBox(historyLabel);
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.getStyleClass().add("box");

        vBox.getChildren().add(textBoxButtonsHBox);
        vBox.getChildren().add(textArea);
        vBox.getChildren().add(historyTitle);
        vBox.getChildren().add(recentlyUsed);
        textArea.setPrefSize(textBoxWidth, 3 * textBoxHeight / 4);

        return vBox;
    }

    public HBox makeCanvasHBox() {
        EventHandler<ActionEvent> clearCommand = event -> {
            canvasScreen.clear();
            reset();
        };
        EventHandler<ActionEvent> resetCommand = event -> reset();
        EventHandler<ActionEvent> saveCommand = event -> canvasScreen.screenShot();
        Button clearButton = canvasScreen.makeButton(canvasButtonsLabels.get(0), clearCommand);
        Button resetButton = canvasScreen.makeButton(canvasButtonsLabels.get(1), resetCommand);
        Button saveButton = canvasScreen.makeButton(canvasButtonsLabels.get(2), saveCommand);

        EventHandler<ActionEvent> langCommand = event -> {
            setLanguage(languagesComboBox.getValue());
        };
        languagesComboBox = canvasScreen.makeComboBoxArrayList(languages, langCommand);

        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        thicknessTextField = canvasScreen.makeTextField("Thickness", "1", thicknessCommand);

        EventHandler<ActionEvent> setBrushColor = event -> {
            brushColor = colorPicker.getValue();
            canvasScreen.setBrushColor(brushColor);
        };
        EventHandler<ActionEvent> setColorBackGround = event -> {
            backgroundColor = colorPickerBackGround.getValue();
            canvasScreen.getBorderRectangle().setFill(backgroundColor);
        };
        colorPicker = canvasScreen.makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = canvasScreen.makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");

        HBox hBox = new HBox(colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton, languagesComboBox);
        hBox.setAlignment(Pos.TOP_RIGHT);
        return hBox;
    }

    private HBox makeTextButtonsHBox() {
        EventHandler<ActionEvent> passCommands = event -> {
            ArrayList<Command> commands = parser.parse(textArea);
            textBox.updateRecentlyUsed(commands, recentlyUsed);
            setCommands(commands, this);
        };
        EventHandler<ActionEvent> clearText = event -> textArea.clear();
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                try {
                    Path filePath = Path.of(f.getPath());
                    String content = Files.readString(filePath);
                    textArea.setText(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        setUpFileChooser();
        EventHandler<ActionEvent> saveFile = event -> {
            File f = fileChooser.showSaveDialog(stage);
            if (f != null) {
                try {
                    FileWriter writer = new FileWriter(f);
                    writer.write(textArea.getText());
                    writer.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Button runButton = textBox.makeButton("RunButton", passCommands);
        Button clearTextButton = textBox.makeButton("ClearTextButton", clearText);
        Button fileOpenButton = textBox.makeButton("ImportButton", openFileChooser);
        Button saveButton = textBox.makeButton("SaveButton", saveFile);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinWidth(textBoxWidth);
        saveButton.setMinWidth(textBoxWidth / 4);
        runButton.setMinWidth(textBoxWidth / 4);
        clearTextButton.setMinWidth(textBoxWidth / 4);
        fileOpenButton.setMinWidth(textBoxWidth / 4);
        hBox.getChildren().add(fileOpenButton);
        hBox.getChildren().add(saveButton);
        hBox.getChildren().add(runButton);
        hBox.getChildren().add(clearTextButton);


        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        textArea.setOnKeyPressed(event -> {
            if (keyCombination.match(event)) {
                ArrayList<Command> commands = parser.parse(textArea);
                textBox.updateRecentlyUsed(commands, recentlyUsed);
                setCommands(commands, this);
            }
        });
        return hBox;
    }

    private void setUpFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("ImportButton"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
    }

    public CanvasScreen getCanvasScreen() {
        return canvasScreen;
    }

    private void updateButtonLanguage(Button button, String property) {
        String label = myResources.getString(property);
        button.setText(label);
    }

    /**
     * A method to set a language during runtime.
     *
     * @author Luyao Wang
     */
    private void setLanguage(String lang) {
        language = lang;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);

        int i = 0;
        for (Node n : canvasHBox.getChildren()) {
            if (n instanceof Button) {
                updateButtonLanguage((Button) n, canvasButtonsLabels.get(i));
                i++;
            }
        }
        Tooltip.install(colorPicker, new Tooltip(myResources.getString("BrushColorPicker")));
        Tooltip.install(colorPickerBackGround, new Tooltip(myResources.getString("CanvasColorPicker")));

        historyText = myResources.getString("CommandHistory");
        historyLabel.setText(historyText);
        int j = 0;
        for (Node n : textBoxButtonsHBox.getChildren()) {
            updateButtonLanguage((Button) n, textBoxButtonsLabels.get(j));
            j++;
        }
        parser.setLanguage(myResources);
    }

    public void reset() {
        turtles.clear(); // TODO: Check if this is correct functionality
        currTurtleIdxs.clear();

        turtles.put(1, new Turtle(1, 0, 0, canvasScreen));
        canvasScreen.getShapes().getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
        currTurtleIdxs.add(1);
    }

    public void setCommands(ArrayList<Command> commands, OolalaGame display) {
        Iterator<Command> itCmd = commands.iterator();
        while (itCmd.hasNext()) {
            Command instruction = itCmd.next();
            //TODO: Handle tell command
            if (instruction.prefix == TELL) {
                currTurtleIdxs.clear();
                currTurtleIdxs.addAll(instruction.params);
                for (Integer param : instruction.params) {
                    if (!turtles.containsKey(param)) {
                        System.out.println("Creating new turtle");
                        turtles.put(param, new Turtle(param, 0, 0, canvasScreen));
                        canvasScreen.getShapes().getChildren().add(turtles.get(param).getIcon());
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                turtles.get(idx).readInstruction(instruction, display);
            }
            itCmd.remove();
        }
    }
}
