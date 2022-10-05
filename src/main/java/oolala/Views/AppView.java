package oolala.Views;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oolala.Models.AppModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Command.Command;
import oolala.Views.ViewComponents.Terminal;

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
    public int sizeWidth;
    public int sizeHeight;
    public int textBoxWidth = 275;
    public int textBoxHeight = 600;
    public BorderPane root;
    public Terminal terminal;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private FileChooser fileChooser;
    private Stage stage;
    public CanvasScreen canvasScreen;
    public ViewUtils viewUtils;
    public HBox rightToolBarHBox;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    public AppModel currentApp;
    public ComboBox<ImageView> imageSelector;

    public AppView(int sizeWidth, int sizeHeight, Stage stage, String language) {
        this.sizeWidth = sizeWidth;
        this.sizeHeight = sizeHeight;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        this.stage = stage;
        fileChooser = new FileChooser();
        root = new BorderPane();
        root.getStyleClass().add("border-pane");
        viewUtils = new ViewUtils(myResources);
        canvasScreen = new CanvasScreen(myResources);
    }

    public BorderPane setUpRootBorderPane() {
        return root;
    }

    public HBox makeRightToolbarHBox() {
        EventHandler<ActionEvent> clearCommand = event -> {
            currentApp.reset(false);
            enableInputs();
        };
        EventHandler<ActionEvent> resetCommand = event -> currentApp.reset(true);
        EventHandler<ActionEvent> saveCommand = makeSaveFileImgEventHandler();
        Button clearButton = viewUtils.makeButton("ClearCanvasButton", clearCommand);
        Button resetButton = viewUtils.makeButton("ResetTurtleButton", resetCommand);
        Button saveButton = viewUtils.makeButton("SaveButton", saveCommand);

        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        thicknessTextField = viewUtils.makeTextField("Thickness", "3", thicknessCommand);
        thicknessTextField.setTooltip(new Tooltip(myResources.getString("ThicknessTextField")));

        EventHandler<ActionEvent> setBrushColor = event -> {
            canvasScreen.setBrushColor(colorPicker.getValue());
        };
        EventHandler<ActionEvent> setColorBackGround = event -> {
            canvasScreen.getBorderRectangle().setFill(colorPickerBackGround.getValue());
        };
        colorPicker = viewUtils.makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = viewUtils.makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");
        HBox modeSlider = createModeSlider();
        HBox hBox = new HBox(modeSlider, colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.setAlignment(Pos.TOP_RIGHT);
        hBox.setSpacing(5);
        return hBox;
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
                    writer.write(terminal.getText());
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

    public void setCommands(ArrayList<Command> commands) {
        currentApp.runApp(commands, this);
    }

    public void disableInputs() {
        imageSelector.setDisable(true);
        terminal.disableRunButton();
    }

    public void enableInputs() {
        imageSelector.setDisable(false);
        terminal.enableRunButton();
    }

    public ComboBox<ImageView> makeImageSelector(String type, List<String> images) {
        ComboBox<ImageView> imageSelector = viewUtils.makeImageSelector(images, type);
        imageSelector.setOnAction(event -> {
            currentApp.changeImage(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }

    public HBox createModeSlider() {
        Slider modeSlider = new Slider(0, 1, 0);
        modeSlider.setMaxWidth(50);
        Label label = new Label("Light");
        modeSlider.setOnMousePressed(event -> {
            if (modeSlider.getValue() == 0) {
                modeSlider.setValue(1);
            } else {
                modeSlider.setValue(0);
            }
        });
        modeSlider.valueProperty().addListener((obs, oldval, newVal) -> {
            modeSlider.setValue(newVal.intValue());
            root.getStylesheets().clear();
            if ((int) modeSlider.getValue() == 0) {
//                root.setStyle("-fx-background-color:white");
                label.setText("Light");
                colorPickerBackGround.setValue(Color.WHITE);
                colorPicker.setValue(Color.BLACK);
                canvasScreen.getBorderRectangle().setFill(Color.WHITE);
                canvasScreen.setBrushColor(Color.BLACK);
                root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
            } else {
//                root.setStyle("-fx-background-color:gray");
                label.setText("Dark");
                colorPickerBackGround.setValue(Color.BLACK);
                colorPicker.setValue(Color.WHITE);
                canvasScreen.getBorderRectangle().setFill(Color.BLACK);
                canvasScreen.setBrushColor(Color.WHITE);
                root.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + DARKMODE_STYLESHEET).toExternalForm());
            }
        });
        HBox modeBox = new HBox(label, modeSlider);
        return modeBox;
    }
}
