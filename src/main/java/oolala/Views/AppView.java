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
import oolala.Views.ViewComponents.ViewUtils;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static oolala.Main.*;
import static oolala.Views.ViewComponents.ViewUtils.*;

/**
 * @Author Luyao Wang
 * Setting up of the UI.
 * The origin is at the center of the screen.
 */
public abstract class AppView {
    private final BorderPane root;
    protected Terminal terminal;
    protected ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private final FileChooser fileChooser;
    private final Stage stage;
    protected CanvasScreen canvasScreen;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    protected AppModel currentAppModel;
    protected ComboBox<ImageView> imageSelector;
    protected ViewUtils viewUtils;
    protected HBox leftToolBarHBox;
    protected VBox leftVBox;
    private Button runButton;
    protected SequentialTransition animation;
    private final String STYLESHEET;
    private final String DARK_MODE_STYLESHEET;
    private final String DEFAULT_RESOURCE_FOLDER;

    public AppView(Stage stage, String language, String defaultResourceFolder, String styleSheet, String darkModeStyleSheet) {
        STYLESHEET = styleSheet;
        DARK_MODE_STYLESHEET = darkModeStyleSheet;
        DEFAULT_RESOURCE_FOLDER = defaultResourceFolder;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        viewUtils = new ViewUtils(myResources);
        this.stage = stage;
        fileChooser = new FileChooser();
        root = new BorderPane();
        root.getStyleClass().add("border-pane");
        canvasScreen = new CanvasScreen(myResources);
        animation = new SequentialTransition();
        animation.setRate(3);
        animation.setOnFinished(event -> {
            enableInputs();
            currentAppModel.setRunning(false);
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
        HBox rightToolBarHBox = makeRightToolbarHBox();
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
        runButton = makeButton("RunButton", event -> runModel());
        Button clearTextButton = makeButton("ClearTextButton", event -> terminal.getTextArea().clear());
        Button fileOpenButton = makeButton("ImportButton", openFileChooserEventHandler());
        Button saveButton = makeButton("SaveButton", makeSaveFileEventHandler());
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
        Button clearButton = makeButton("ClearCanvasButton", event -> resetModel(false));
        Button resetButton = makeButton("ResetTurtleButton", event -> resetModel(true));
        Button saveButton = makeButton("SaveButton", makeSaveFileImgEventHandler());
        EventHandler<ActionEvent> setBrushColor = event -> canvasScreen.setBrushColor(colorPicker.getValue());
        EventHandler<ActionEvent> setColorBackGround = event -> canvasScreen.getBorderRectangle().setFill(colorPickerBackGround.getValue());
        thicknessTextField = makeTextField("ThicknessTextField", "3", event -> canvasScreen.setThickness(thicknessTextField.getText()));
        thicknessTextField.setTooltip(new Tooltip(myResources.getString("ThicknessTextField")));
        colorPicker = makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");
        HBox modeSlider = makeDisplayModeSwitcher();
        HBox hBox = new HBox(modeSlider, colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.getStyleClass().add("right-hbox");
        return hBox;
    }

    private void resetModel(boolean resetHome){
        animation.stop();
        animation.getChildren().removeAll(animation.getChildren());
        currentAppModel.reset(resetHome);
        enableInputs();
    }

    public void setUpTextAreaKeyCombination() {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        terminal.getTextArea().setOnKeyPressed(event -> {
            if (keyCombination.match(event)) runModel();
        });
    }

    private void runModel() {
        List<Command> commands = currentAppModel.getParser().parse(terminal.getTextArea().getText().toLowerCase());
        terminal.updateRecentlyUsed(currentAppModel.getParser().getRecentCommandStrings());
        currentAppModel.runApp(commands);
        disableInputs();
        if (animation.getChildren().size() == 0) {
            currentAppModel.setRunning(false);
            enableInputs();
        } else {
            animation.play();
            animation.getChildren().removeAll(animation.getChildren());
        }
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

    public ComboBox<ImageView> makeAppViewImageSelector(String type, List<String> images) {
        ComboBox<ImageView> imageSelector = makeImageSelector(images, type);
        imageSelector.setOnAction(event -> {
            currentAppModel.changeImage(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }

    public HBox makeDisplayModeSwitcher() {
        Slider modeSwitcher = makeToggleBar(0, 1, 0, 50, "ModeSwitcher");
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
        root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + DARK_MODE_STYLESHEET).toExternalForm());
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
