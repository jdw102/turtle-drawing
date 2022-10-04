package oolala;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oolala.Command.Command;

import javax.imageio.ImageIO;
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
    ViewUtils viewUtils;
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
        fileChooser = new FileChooser();
        root = new BorderPane();

        viewUtils = new ViewUtils(myResources);

        canvasScreen = new CanvasScreen(myResources);
        Group canvasShapes = canvasScreen.getShapes();

        if (currentAppName.equals("Logo")){
            currentApp = new TurtleDrawingModel(canvasScreen, myResources, "TurtleIcon", this);
        }
        else if (currentAppName.equals("LSystem")){
            currentApp = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", this);
        }
        rightToolBarHBox = makeRightToolbarHBox();
        textBox = new TextBox(textBoxWidth, textBoxHeight, myResources, currentAppName, currentApp, this, viewUtils);
        root.setLeft(textBox.getBox());
        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasShapes);

        root.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }

    public HBox makeRightToolbarHBox() {
        EventHandler<ActionEvent> clearCommand = event -> {
            currentApp.reset(false);
            enableImageSelectors();
        };
        EventHandler<ActionEvent> resetCommand = event -> currentApp.reset(true);
        EventHandler<ActionEvent> saveCommand = makeSaveFileImgEventHandler();
        Button clearButton = viewUtils.makeButton("ClearCanvasButton", clearCommand);
        Button resetButton = viewUtils.makeButton("ResetTurtleButton", resetCommand);
        Button saveButton = viewUtils.makeButton("SaveButton", saveCommand);

        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        thicknessTextField = viewUtils.makeTextField("Thickness", "3", thicknessCommand);

        EventHandler<ActionEvent> setBrushColor = event -> {
            canvasScreen.setBrushColor(colorPicker.getValue());
        };
        EventHandler<ActionEvent> setColorBackGround = event -> {
            canvasScreen.getBorderRectangle().setFill(colorPickerBackGround.getValue());
        };
        colorPicker = viewUtils.makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = viewUtils.makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");
        makeImageSelector();
        HBox hBox = new HBox(imageSelector, colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.setAlignment(Pos.TOP_RIGHT);
        return hBox;
    }

    private void makeImageSelector() {
        if (Objects.equals(currentAppName, "Logo")){
            imageSelector = viewUtils.makeImageSelector(iconLabels, "IconChange");
            imageSelector.setOnAction(event -> {
                currentApp.changeIcon(imageSelector.getValue().getImage().getUrl());
            });
        }
        else if (Objects.equals(currentAppName, "LSystem")){
            imageSelector = viewUtils.makeImageSelector(stampLabels, "StampChange");
            imageSelector.setOnAction(event -> {
                currentApp.changeStamp(imageSelector.getValue().getImage().getUrl());
            });
        }
    }

    public EventHandler<ActionEvent> openFileChooserEventHandler() {
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                try {
                    Path filePath = Path.of(f.getPath());
                    String content = Files.readString(filePath);
                    textBox.setText(content);
                } catch (IOException e) {throw new RuntimeException(e);}
            }
        };
        return openFileChooser;
    }

    public EventHandler<ActionEvent> makeSaveFileEventHandler() {
        fileChooser.setTitle(myResources.getString("ImportButton"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        EventHandler<ActionEvent> saveFile = event -> {
            File f = fileChooser.showSaveDialog(stage);
            if (f != null) {
                try {
                    FileWriter writer = new FileWriter(f);
                    writer.write(textBox.getText());
                    writer.close();
                } catch (IOException e) {throw new RuntimeException(e);}
            }
        };
        return saveFile;
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
        currentApp.runApp(commands, this);
    }

    public void disableImageSelectors(){
        imageSelector.setDisable(true);
    }
    public void enableImageSelectors(){
        imageSelector.setDisable(false);
    }

}
    