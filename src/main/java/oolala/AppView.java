package oolala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import oolala.Command.Command;

import javax.imageio.ImageIO;
import javax.tools.Tool;
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
    private BorderPane root;
    private TextBox textBox;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private FileChooser fileChooser;
    private Stage stage;
    private CanvasScreen canvasScreen;
    ToolBar toolBar;
    private VBox textBoxVBox;
    private HBox leftToolbarHbox;
    private HBox rightToolBarHBox;
    private ListView<String> recentlyUsed;
    private TextArea textArea;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    private AppModel currentApp;
    private final List<String> iconLabels = new ArrayList<>(Arrays.asList("TurtleIcon", "SimpleTurtleIcon", "TriangleArrowIcon"));
    private final List<String> stampLabels = new ArrayList<>(Arrays.asList("SimpleLeafStamp", "OakLeafStamp", "MapleLeafStamp", "FireworkStamp"));
    private ComboBox<ImageView> imageSelector;
    private String currentAppName;

    public Scene setUpScene(int sizeWidth, int sizeHeight, Stage stage, String language, String appName) {
        currentAppName = appName;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.stage = stage;

        root = new BorderPane();

        toolBar = new ToolBar(myResources);

        textBox = new TextBox(myResources);
        textBoxVBox = makeTextBoxVBox();
        root.setLeft(textBoxVBox);

        canvasScreen = new CanvasScreen(myResources);
        Group canvasShapes = canvasScreen.getShapes();

        if (currentAppName.equals("Logo")){
            currentApp = new TurtleDrawingModel(this, myResources, "TurtleIcon");
        }
        else if (currentAppName.equals("LSystem")){
            currentApp = new LSystemModel(this, myResources, "SimpleLeafStamp");
        }
        rightToolBarHBox = makeRightToolbarHBox();

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
                ArrayList<Command> commands = currentApp.getParser().parse(textArea.getText().toLowerCase());
                textBox.updateRecentlyUsed(commands, recentlyUsed);
                setCommands(commands);
            }
        });

        EventHandler<MouseEvent> addLine = event -> {
            textBox.addLine(recentlyUsed.getSelectionModel().getSelectedItem(), textArea);
        };
        recentlyUsed = textBox.makeListView(200, addLine);

        HBox historyTitle = new HBox(new Label(myResources.getString("CommandHistory")));
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.getStyleClass().add("box");

        vBox.getChildren().add(leftToolbarHbox);
        vBox.getChildren().add(textArea);
        vBox.getChildren().add(historyTitle);
        vBox.getChildren().add(recentlyUsed);
        textArea.setPrefSize(textBoxWidth, 3 * textBoxHeight / 4);
        if (currentAppName.equals("LSystem")){
            toolBar.makeSliders(vBox, currentApp);
        }
        return vBox;
    }

    public HBox makeRightToolbarHBox() {
        EventHandler<ActionEvent> clearCommand = event -> {
            currentApp.reset(false);
            enableImageSelectors();
        };
        EventHandler<ActionEvent> resetCommand = event -> currentApp.reset(true);
        EventHandler<ActionEvent> saveCommand = makeSaveFileImgEventHandler();
        Button clearButton = toolBar.makeButton("ClearCanvasButton", clearCommand);
        Button resetButton = toolBar.makeButton("ResetTurtleButton", resetCommand);
        Button saveButton = toolBar.makeButton("SaveButton", saveCommand);

        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        thicknessTextField = toolBar.makeTextField("Thickness", "3", thicknessCommand);

        EventHandler<ActionEvent> setBrushColor = event -> {
            canvasScreen.setBrushColor(colorPicker.getValue());
        };
        EventHandler<ActionEvent> setColorBackGround = event -> {
            canvasScreen.getBorderRectangle().setFill(colorPickerBackGround.getValue());
        };
        colorPicker = toolBar.makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = toolBar.makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");
        makeBrushImageSelector();
        HBox hBox = new HBox(imageSelector, colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.setAlignment(Pos.TOP_RIGHT);
        return hBox;
    }

    private void makeBrushImageSelector() {
        if (Objects.equals(currentAppName, "Logo")){
            imageSelector = toolBar.makeImageSelector(iconLabels, "IconChange");
            imageSelector.setOnAction(event -> {
                currentApp.changeIcon(imageSelector.getValue().getImage().getUrl());
            });
        }
        else if (Objects.equals(currentAppName, "LSystem")){
            imageSelector = toolBar.makeImageSelector(stampLabels, "StampChange");
            imageSelector.setOnAction(event -> {
                currentApp.changeStamp(imageSelector.getValue().getImage().getUrl());
            });
        }
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
            ArrayList<Command> commands = currentApp.getParser().parse(textArea.getText().toLowerCase());
            textBox.updateRecentlyUsed(commands, recentlyUsed);
            setCommands(commands);
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
    private EventHandler<ActionEvent> makeSaveFileImgEventHandler() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        EventHandler<ActionEvent> saveFile = event -> {
            WritableImage snapshot = canvasScreen.screenShot();
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return saveFile;
    }

    public void setCommands(ArrayList<Command> commands) {
        currentApp.runApp(commands);
    }
    public boolean isCanvasClear(){
        if (canvasScreen.getShapes().getChildren().size() > 2){
            return false;
        }
        else return true;
    }

    public void disableImageSelectors(){
        imageSelector.setDisable(true);
    }
    public void enableImageSelectors(){
        imageSelector.setDisable(false);
    }

}
