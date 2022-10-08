package oolala.Views;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.SequentialTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oolala.Models.AppModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Command.Command;
import oolala.Views.ViewComponents.Terminal;
import oolala.Views.ViewComponents.ViewUtils;
import javafx.scene.control.ComboBox;


import static oolala.Main.DARK_MODE_STYLESHEET;
import static oolala.Main.DEFAULT_RESOURCE_FOLDER;
import static oolala.Main.STYLESHEET;
import static oolala.Views.ViewComponents.ViewUtils.makeButton;
import static oolala.Views.ViewComponents.ViewUtils.makeColorPicker;
import static oolala.Views.ViewComponents.ViewUtils.makeImageSelector;
import static oolala.Views.ViewComponents.ViewUtils.makeTextField;
import static oolala.Views.ViewComponents.ViewUtils.makeToggleBar;

/**
 * An abstract view class that contains all basic components of the view besides the terminal and image selector
 * which will vary between the Logo view and LSystem view.
 *
 * @author Luyao Wang
 */
public abstract class AppView {
    private final BorderPane root;
    protected Terminal terminal;
    protected ResourceBundle myResources;
    public static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
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

    public AppView(Stage stage, String language) {
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
            currentAppModel.setRunningStatus(false);
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
    /**
     * A method to create border pane that will act as the root for the display. It calls
     * the other methods to create the toolbars and set them to their corresponding positions
     * in the pane.
     *
     * @author Luyao Wang
     */
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
    /**
     * A method to create the left toolbar HBox including all of its buttons.
     * It also adds all the event handlers to these buttons.
     *
     * @return The HBox that contains all the inputs.
     * @author Luyao Wang
     */
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
    /**
     * A method to create the right toolbar HBox including all of its buttons, color pickers, and the text field.
     * It also adds all the event handlers to these nodes.
     *
     * @return The HBox that contains all the inputs.
     * @author Luyao Wang
     */
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
    /**
     * A method to stop the animation, reset the model, and enable the inputs.
     *
     * @param resetHome - If true the home location of the model is reset as well.
     * @author Jerry Worthy
     */
    private void resetModel(boolean resetHome) {
        animation.stop();
        animation.getChildren().removeAll(animation.getChildren());
        currentAppModel.reset(resetHome);
        enableInputs();
    }
    public void setUpTextAreaKeyCombination() {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        terminal.getTextArea().setOnKeyPressed(event -> {
            if (keyCombination.match(event) && !runButton.isDisabled()) runModel();
        });
    }
    /**
     * A method to get the commands by parsing the text area, then run the app model using those commands.
     * It disables the inputs on call and plays the animation if there is any animations to be played.
     * If there is no animation to be played, the running status of the app is set to false and the
     * inputs are enabled. If the animation is played, on its completion the inputs are enabled. If an error is
     * thrown in the parsing, the error message is displayed to the user.
     *
     * @author Luyao Wang
     */
    private void runModel() {
        try {
            List<Command> commands = currentAppModel.getParser().parse(terminal.getTextArea().getText().toLowerCase());
            terminal.updateRecentlyUsed(currentAppModel.getParser().getRecentCommandStrings());
            currentAppModel.runApp(commands);
            disableInputs();
            if (animation.getChildren().size() == 0) {
                currentAppModel.setRunningStatus(false);
                enableInputs();
            } else {
                animation.play();
                animation.getChildren().removeAll(animation.getChildren());
            }
        } catch (IllegalStateException e) {
            showError(e.getMessage());
        }
    }
    /**
     * A method to display error messages to the user using an alert.
     *
     * @param message - The error message.
     * @author Luyao Wang
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * A method to create the event handler that allows a text file
     * to be chosen and its text to be written to the terminal text area.
     *
     * @return Returns the event handler to be attached to the button.
     * @author Jerry Worthy
     */
    public EventHandler<ActionEvent> openFileChooserEventHandler() {
        EventHandler<ActionEvent> openFileChooser = event -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                try {
                    Path filePath = Path.of(f.getPath());
                    String content = Files.readString(filePath);
                    terminal.setText(content);
                } catch (IOException e) {
                    showError(e.getMessage());
                }
            }
        };
        return openFileChooser;
    }
    /**
     * A method to create the event handler that allows a text file
     * to be created that contains the text in the text area.
     *
     * @return Returns the event handler to be attached to the button.
     * @author Luyao Wang
     */
    public EventHandler<ActionEvent> makeSaveFileEventHandler() {
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
                    showError(e.getMessage());
                }
            }
        };
        return saveFile;
    }
    /**
     * A method to create the event handler that allows a png image
     * of the canvas to be saved.
     *
     * @return Returns the event handler to be attached to the button.
     * @author Luyao Wang
     */
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
                    showError(e.getMessage());
                }
            }
        };
        return saveFile;
    }
    /**
     * A method to create the image selector for each app. The type indicates whether it's tooltip
     * will be titled "Change Icon" or "Change Stamp", and each app will have it either change the
     * turtle icon or stamp respectively.
     *
     * @return Returns combo box of image views that acts as either icon or stamp selector.
     * @author Jerry Worthy
     */
    public ComboBox<ImageView> makeAppViewImageSelector(String type, List<String> images) {
        ComboBox<ImageView> imageSelector = makeImageSelector(images, type);
        imageSelector.setOnAction(event -> {
            currentAppModel.changeImage(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }
    /**
     * A method to create the slider that will act as a switch to change
     * from light mode to dark mode. It attaches the listener
     * to the slider.
     *
     * @return Returns the HBox that contains the slider and its label.
     * @author Jerry Worthy
     */
    public HBox makeDisplayModeSwitcher() {
        Slider modeSwitcher = makeToggleBar(0, 50, "ModeSwitcher");
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
    /**
     * A method to switch the theme to dark mode and update the label text.
     *
     * @author Jerry Worthy
     */
    private void setDarkMode(Label label) {
        label.setText(myResources.getString("DarkMode"));
        colorPickerBackGround.setValue(Color.BLACK);
        colorPicker.setValue(Color.WHITE);
        canvasScreen.getBorderRectangle().setFill(Color.BLACK);
        canvasScreen.setBrushColor(Color.WHITE);
        root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + DARK_MODE_STYLESHEET).toExternalForm());
    }
    /**
     * A method to switch the theme to light mode and update the label text.
     *
     * @author Luyao Wang
     */
    private void setLightMode(Label label) {
        label.setText(myResources.getString("LightMode"));
        colorPickerBackGround.setValue(Color.WHITE);
        colorPicker.setValue(Color.BLACK);
        canvasScreen.getBorderRectangle().setFill(Color.WHITE);
        canvasScreen.setBrushColor(Color.BLACK);
        root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    }
}
