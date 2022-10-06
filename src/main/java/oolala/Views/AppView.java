package oolala.Views;

import javafx.animation.SequentialTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oolala.Models.AppModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Command.Command;
import oolala.Views.ViewComponents.Terminal;
import oolala.Views.ViewComponents.ToolBar;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static oolala.Main.*;

/**
 * @Author Luyao Wang
 * Setting up of the UI.
 * The origin is at the center of the screen.
 */
public abstract class AppView {
    public BorderPane root;
    public Terminal terminal;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private FileChooser fileChooser;
    private Stage stage;
    public CanvasScreen canvasScreen;
    public HBox rightToolBarHBox;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    protected AppModel currentAppModel;
    protected ComboBox<ImageView> imageSelector;
    protected ToolBar toolBar;
    protected HBox leftToolBarHBox;
    protected VBox leftVBox;
    private Button runButton;
    protected SequentialTransition animation;

    public AppView(Stage stage, String language) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.stage = stage;
        fileChooser = new FileChooser();
        root = new BorderPane();
        root.getStyleClass().add("border-pane");
        canvasScreen = new CanvasScreen(myResources);
        toolBar = new ToolBar(myResources);

        animation = new SequentialTransition();
        animation.setRate(3);
        animation.setOnFinished(event -> {
            enableInputs();
        });
    }

    public void enableInputs() {
        imageSelector.setDisable(false);
        runButton.setDisable(false);
    }

    public void disableInputs() {
        imageSelector.setDisable(true);
        runButton.setDisable(true);
    }

    public BorderPane setUpRootBorderPane() {
        setUpTextAreaKeyCombination();
        leftToolBarHBox = makeLeftToolbarHBox();
        rightToolBarHBox = makeRightToolbarHBox();
        rightToolBarHBox.getChildren().add(1, imageSelector);
        leftVBox = terminal.getBox();
        leftVBox.getChildren().add(0, leftToolBarHBox);
        root.setLeft(terminal.getBox());

        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasScreen.getShapes());
        root.setPadding(new Insets(10, 10, 10, 10));
        return root;
    }

    public HBox makeLeftToolbarHBox() {
        EventHandler<ActionEvent> passCommands = makePassCommandEventEventHandler();
        EventHandler<ActionEvent> clearText = event -> terminal.getTextArea().clear();
        runButton = toolBar.makeButton("RunButton", passCommands);
        Button clearTextButton = toolBar.makeButton("ClearTextButton", clearText);
        Button fileOpenButton = toolBar.makeButton("ImportButton", openFileChooserEventHandler());
        Button saveButton = toolBar.makeButton("SaveButton", makeSaveFileEventHandler());
        HBox hBox = new HBox();

        saveButton.getStyleClass().add("left-hbox-button");
        runButton.getStyleClass().add("left-hbox-button");
        clearTextButton.getStyleClass().add("left-hbox-button");
        fileOpenButton.getStyleClass().add("left-hbox-button");
        hBox.getStyleClass().add("left-hbox");
        hBox.getChildren().addAll(fileOpenButton, saveButton, runButton, clearTextButton);

        return hBox;
    }

    public HBox makeRightToolbarHBox() {
        EventHandler<ActionEvent> clearCommand = makeClearCommandEventEventHandler();
        EventHandler<ActionEvent> resetCommand = event -> currentAppModel.reset(true);
        EventHandler<ActionEvent> saveCommand = makeSaveFileImgEventHandler();
        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        Button clearButton = toolBar.makeButton("ClearCanvasButton", clearCommand);
        Button resetButton = toolBar.makeButton("ResetTurtleButton", resetCommand);
        Button saveButton = toolBar.makeButton("SaveButton", saveCommand);
        EventHandler<ActionEvent> setBrushColor = event -> canvasScreen.setBrushColor(colorPicker.getValue());
        EventHandler<ActionEvent> setColorBackGround = event -> canvasScreen.getBorderRectangle().setFill(colorPickerBackGround.getValue());
        thicknessTextField = toolBar.makeTextField("Thickness", "3", thicknessCommand);
        thicknessTextField.setTooltip(new Tooltip(myResources.getString("ThicknessTextField")));
        colorPicker = toolBar.makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = toolBar.makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");
        HBox modeSlider = makeDisplayModeSwitcher();
        HBox hBox = new HBox(modeSlider, colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.getStyleClass().add("right-hbox");
        return hBox;
    }

    private EventHandler<ActionEvent> makeClearCommandEventEventHandler() {
        EventHandler<ActionEvent> clearCommand = event -> {
            currentAppModel.reset(false);
            enableInputs();
        };
        return clearCommand;
    }

    public void setUpTextAreaKeyCombination() {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        EventHandler<KeyEvent> runKeyCombinationEventHandler = event -> {
            if (keyCombination.match(event)) {
                ArrayList<Command> commands = currentAppModel.getParser().parse(terminal.getTextArea().getText().toLowerCase());
                terminal.updateRecentlyUsed(commands, terminal.getRecentlyUsed());
                currentAppModel.runApp(commands);
                disableInputs();
            }
        };
        terminal.getTextArea().setOnKeyPressed(runKeyCombinationEventHandler);
    }

    private EventHandler<ActionEvent> makePassCommandEventEventHandler() {
        EventHandler<ActionEvent> passCommand = event -> {
            ArrayList<Command> commands = currentAppModel.getParser().parse(terminal.getTextArea().getText().toLowerCase());
            terminal.updateRecentlyUsed(commands, terminal.getRecentlyUsed());
            currentAppModel.runApp(commands);
            disableInputs();
        };
        return passCommand;
    }

    public EventHandler<ActionEvent> openFileChooserEventHandler() {
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                try {
                    Path filePath = Path.of(f.getPath());
                    String content = Files.readString(filePath);
                    terminal.setText(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
                    writer.write(terminal.getTextArea().getText().toLowerCase());
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

    public ComboBox<ImageView> makeImageSelector(String type, List<String> images) {
        ComboBox<ImageView> imageSelector = toolBar.makeImageSelector(images, type);
        imageSelector.setOnAction(event -> {
            currentAppModel.changeImage(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }

    public HBox makeDisplayModeSwitcher() {
        Slider modeSwitcher = toolBar.makeToggleBar(0, 1, 0, 50);
        Label label = new Label(myResources.getString("LightMode"));
        modeSwitcher.valueProperty().addListener((obs, oldVal, newVal) -> {
            modeSwitcher.setValue(newVal.intValue());
            root.getStylesheets().clear();
            if ((int) modeSwitcher.getValue() == 0) setLightMode(label);
            else setDarkMode(label);
        });
        HBox modeSwitchBox = new HBox(label, modeSwitcher);
        return modeSwitchBox;
    }

    private void setDarkMode(Label label) {
        label.setText(myResources.getString("DarkMode"));
        colorPickerBackGround.setValue(Color.BLACK);
        colorPicker.setValue(Color.WHITE);
        canvasScreen.getBorderRectangle().setFill(Color.BLACK);
        canvasScreen.setBrushColor(Color.WHITE);
        root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + DARKMODE_STYLESHEET).toExternalForm());
    }

    private void setLightMode(Label label) {
        label.setText(myResources.getString("LightMode"));
        colorPickerBackGround.setValue(Color.WHITE);
        colorPicker.setValue(Color.BLACK);
        canvasScreen.getBorderRectangle().setFill(Color.WHITE);
        canvasScreen.setBrushColor(Color.BLACK);
        root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    }
}
