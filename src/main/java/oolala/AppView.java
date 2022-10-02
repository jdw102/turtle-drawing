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
import oolala.Command.Command;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @Author Luyao Wang
 * Setting up of the UI.
 * The origin is at the center of the screen.
 */
public class AppView {
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
    ToolBar toolBar;
    private VBox textBoxVBox;
    private HBox leftToolbarHbox;
    private HBox rightToolBarHBox;
    private ListView<String> recentlyUsed;
    private TextArea textArea;
    private Label historyLabel;
    private Group canvasShapes;
    private static final String DEFAULT_LANGUAGE = "English";
    private String language;
    private ArrayList<String> languages = new ArrayList<>(Arrays.asList("English", "简体中文", "繁體中文", "日本語"));
    private ArrayList<String> canvasButtonsLabels = new ArrayList<>(Arrays.asList("ClearCanvasButton", "ResetTurtleButton", "SaveButton"));
    private ArrayList<String> textBoxButtonsLabels = new ArrayList<>(Arrays.asList("ImportButton", "SaveButton", "RunButton", "ClearTextButton"));
    private ComboBox<String> languagesComboBox;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    private Color brushColor;
    private Color backgroundColor;
    private HashMap<String, AppModel> apps;
    private AppModel currentApp;

    public Scene setUpScene(int sizeWidth, int sizeHeight, Stage stage) {

        this.stage = stage;
        this.language = DEFAULT_LANGUAGE;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        parser = new Parser(myResources);

        root = new BorderPane();

        toolBar = new ToolBar(myResources);

        textBox = new TextBox(myResources);
        textBoxVBox = makeTextBoxVBox();
        root.setLeft(textBoxVBox);

        canvasScreen = new CanvasScreen(myResources);
        rightToolBarHBox = makeCanvasHBox();
        canvasShapes = canvasScreen.getShapes();
        apps = new HashMap<>();

        apps.put("DrawingApp", new TurtleDrawingModel(this));
        currentApp = apps.get("DrawingApp");

        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasShapes);

        root.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }

    public VBox makeTextBoxVBox() {
        VBox vBox = new VBox();
        leftToolbarHbox = makeLeftToolbarHBox();
        textArea = textBox.makeTextArea();

        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        textArea.setOnKeyPressed(event -> {
            if (keyCombination.match(event)) {
                ArrayList<Command> commands = parser.parse(textArea);
                textBox.updateRecentlyUsed(commands, recentlyUsed);
                setCommands(commands, this);
            }
        });

        EventHandler<MouseEvent> addLine = event -> {
            textBox.addLine(recentlyUsed.getSelectionModel().getSelectedItem(), textArea);
        };
        recentlyUsed = textBox.makeListView(200, addLine);

        historyLabel = new Label(historyText);
        HBox historyTitle = new HBox(historyLabel);
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.getStyleClass().add("box");

        vBox.getChildren().add(leftToolbarHbox);
        vBox.getChildren().add(textArea);
        vBox.getChildren().add(historyTitle);
        vBox.getChildren().add(recentlyUsed);
        textArea.setPrefSize(textBoxWidth, 3 * textBoxHeight / 4);

        return vBox;
    }

    public HBox makeCanvasHBox() {
        EventHandler<ActionEvent> clearCommand = event -> {
            canvasScreen.clear();
            currentApp.reset();
        };
        EventHandler<ActionEvent> resetCommand = event -> currentApp.reset();
        EventHandler<ActionEvent> saveCommand = event -> canvasScreen.screenShot();
        Button clearButton = toolBar.makeButton(canvasButtonsLabels.get(0), clearCommand);
        Button resetButton = toolBar.makeButton(canvasButtonsLabels.get(1), resetCommand);
        Button saveButton = toolBar.makeButton(canvasButtonsLabels.get(2), saveCommand);

        EventHandler<ActionEvent> langCommand = event -> {
            setLanguage(languagesComboBox.getValue());
        };
        languagesComboBox = canvasScreen.makeComboBoxArrayList(languages, langCommand);

        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        thicknessTextField = canvasScreen.makeTextField("Thickness", "3", thicknessCommand);

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

    private HBox makeLeftToolbarHBox() {
        EventHandler<ActionEvent> passCommands = makePassCommandsEventEventHandler();
        EventHandler<ActionEvent> clearText = event -> textArea.clear();
        EventHandler<ActionEvent> openFileChooser = openFileChooserEventHandler();
        EventHandler<ActionEvent> saveFile = makeSaveFileEventHandler();
        Button runButton = toolBar.makeButton("RunButton", passCommands);
        Button clearTextButton = toolBar.makeButton("ClearTextButton", clearText);
        Button fileOpenButton = toolBar.makeButton("ImportButton", openFileChooser);
        Button saveButton = toolBar.makeButton("SaveButton", saveFile);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinWidth(textBoxWidth);
        saveButton.setMinWidth(textBoxWidth / 4);
        runButton.setMinWidth(textBoxWidth / 4);
        clearTextButton.setMinWidth(textBoxWidth / 4);
        fileOpenButton.setMinWidth(textBoxWidth / 4);
        hBox.getChildren().addAll(fileOpenButton, saveButton, runButton, clearTextButton);

        return hBox;
    }

    private EventHandler<ActionEvent> makePassCommandsEventEventHandler() {
        EventHandler<ActionEvent> passCommands = event -> {
            ArrayList<Command> commands = parser.parse(textArea);
            textBox.updateRecentlyUsed(commands, recentlyUsed);
            setCommands(commands, this);
        };
        return passCommands;
    }

    private EventHandler<ActionEvent> openFileChooserEventHandler() {
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                try {
                    Path filePath = Path.of(f.getPath());
                    String content = Files.readString(filePath);
                    textArea.setText(content);
                } catch (IOException e) {throw new RuntimeException(e);}
            }
        };
        return openFileChooser;
    }

    private EventHandler<ActionEvent> makeSaveFileEventHandler() {
        fileChooser = new FileChooser();
        fileChooser.setTitle(myResources.getString("ImportButton"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        EventHandler<ActionEvent> saveFile = event -> {
            File f = fileChooser.showSaveDialog(stage);
            if (f != null) {
                try {
                    FileWriter writer = new FileWriter(f);
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException e) {throw new RuntimeException(e);}
            }
        };
        return saveFile;
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
        for (Node n : rightToolBarHBox.getChildren()) {
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
        for (Node n : leftToolbarHbox.getChildren()) {
            updateButtonLanguage((Button) n, textBoxButtonsLabels.get(j));
            j++;
        }
        parser.setLanguage(myResources);
    }

    public void setCommands(ArrayList<Command> commands, AppView display) {
        currentApp.runApp(commands);
    }

}
