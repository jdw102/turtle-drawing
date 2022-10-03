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
    ToolBar toolBar;
    private VBox textBoxVBox;
    private HBox leftToolbarHbox;
    private HBox rightToolBarHBox;
    private ListView<String> recentlyUsed;
    private TextArea textArea;
    private Label historyLabel;
    private static final String DEFAULT_LANGUAGE = "English";
    private String language;
    private ObservableList<String> applicationLabels = FXCollections.observableArrayList("DrawingApp", "LSystem");
    private ComboBox<String> appComboBox;
    private TextField thicknessTextField;
    private ColorPicker colorPickerBackGround;
    private ColorPicker colorPicker;
    private Color brushColor;
    private Color backgroundColor;
    private HashMap<String, AppModel> apps;
    private AppModel currentApp;
    private LSystemSlider lengthSlider;
    private LSystemSlider angleSlider;
    private LSystemSlider levelSlider;

    public Scene setUpScene(int sizeWidth, int sizeHeight, Stage stage) {

        this.stage = stage;
        this.language = DEFAULT_LANGUAGE;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);

        root = new BorderPane();

        toolBar = new ToolBar(myResources);

        textBox = new TextBox(myResources);
        textBoxVBox = makeTextBoxVBox();
        root.setLeft(textBoxVBox);

        canvasScreen = new CanvasScreen(myResources);
        rightToolBarHBox = makeRightToolbarHBox();
        Group canvasShapes = canvasScreen.getShapes();
        apps = new HashMap<>();

        apps.put(applicationLabels.get(0), new TurtleDrawingModel(this, myResources));
        apps.put(applicationLabels.get(1), new LSystemModel(this, myResources));
        currentApp = apps.get("DrawingApp");
        currentApp.displayTurtles();
//        currentApp = apps.get("LSystem");

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
                setCommands(commands, this);
            }
        });

        EventHandler<MouseEvent> addLine = event -> {
            textBox.addLine(recentlyUsed.getSelectionModel().getSelectedItem(), textArea);
        };
        recentlyUsed = textBox.makeListView(200, addLine);
        EventHandler<MouseEvent> lengthChange = event -> {
            ( (LSystemParser) apps.get("LSystem").getParser()).setDist((int) lengthSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> angleChange = event -> {
            ( (LSystemParser) apps.get("LSystem").getParser()).setAng((int) angleSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> levelChange = event -> {
            ( (LSystemParser) apps.get("LSystem").getParser()).setLevel((int) levelSlider.getSlider().getValue());
        };
        lengthSlider = new LSystemSlider(1, 100, 10, lengthChange, myResources.getString("LengthSlider"));
        angleSlider = new LSystemSlider(1, 180, 30, angleChange, myResources.getString("AngleSlider"));
        levelSlider = new LSystemSlider(1, 10, 3, levelChange, myResources.getString("LevelSlider"));


        historyLabel = new Label(myResources.getString("CommandHistory"));
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

    public HBox makeRightToolbarHBox() {
        EventHandler<ActionEvent> clearCommand = event -> currentApp.reset(false);
        EventHandler<ActionEvent> resetCommand = event -> currentApp.reset(true);
        EventHandler<ActionEvent> saveCommand = makeSaveFileImgEventHandler();
        Button clearButton = toolBar.makeButton("ClearCanvasButton", clearCommand);
        Button resetButton = toolBar.makeButton("ResetTurtleButton", resetCommand);
        Button saveButton = toolBar.makeButton("SaveButton", saveCommand);

        EventHandler<ActionEvent> thicknessCommand = event -> canvasScreen.setThickness(thicknessTextField.getText());
        thicknessTextField = toolBar.makeTextField("Thickness", "3", thicknessCommand);

        EventHandler<ActionEvent> setBrushColor = event -> {
            brushColor = colorPicker.getValue();
            canvasScreen.setBrushColor(brushColor);
        };
        EventHandler<ActionEvent> setColorBackGround = event -> {
            backgroundColor = colorPickerBackGround.getValue();
            canvasScreen.getBorderRectangle().setFill(backgroundColor);
        };
        colorPicker = toolBar.makeColorPicker(setBrushColor, Color.BLACK, "BrushColorPicker");
        colorPickerBackGround = toolBar.makeColorPicker(setColorBackGround, Color.AZURE, "CanvasColorPicker");

        HBox hBox = new HBox(colorPickerBackGround, colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.setAlignment(Pos.TOP_RIGHT);
        return hBox;
    }

    private HBox makeLeftToolbarHBox() {
        EventHandler<ActionEvent> passCommands = makePassCommandsEventEventHandler();
        EventHandler<ActionEvent> clearText = event -> textArea.clear();
        EventHandler<ActionEvent> openFileChooser = openFileChooserEventHandler();
        EventHandler<ActionEvent> saveFile = makeSaveFileEventHandler();
        EventHandler<ActionEvent> selectApp = makeAppSelectionEventHandler();
        Button runButton = toolBar.makeButton("RunButton", passCommands);
        Button clearTextButton = toolBar.makeButton("ClearTextButton", clearText);
        Button fileOpenButton = toolBar.makeButton("ImportButton", openFileChooser);
        Button saveButton = toolBar.makeButton("SaveButton", saveFile);
        appComboBox = makeAppSelector();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinWidth(textBoxWidth);
        saveButton.setMinWidth(textBoxWidth / 5);
        runButton.setMinWidth(textBoxWidth / 5);
        clearTextButton.setMinWidth(textBoxWidth / 5);
        fileOpenButton.setMinWidth(textBoxWidth / 5);
        appComboBox.setMaxWidth(textBoxWidth / 5);
        hBox.getChildren().addAll(appComboBox, fileOpenButton, saveButton, runButton, clearTextButton);

        return hBox;
    }
    private EventHandler<ActionEvent> makeAppSelectionEventHandler() {
        EventHandler<ActionEvent> selectApp = event -> {
            currentApp.reset(true);
            currentApp.removeTurtles();
            currentApp = apps.get(appComboBox.getValue());
            if (appComboBox.getValue().equals("LSystem")){
                addLSystemSliders();
            }
            else{
                removeLSystemSliders();
            }
            currentApp.displayTurtles();
        };
        return selectApp;
    }
    private EventHandler<ActionEvent> makePassCommandsEventEventHandler() {
        EventHandler<ActionEvent> passCommands = event -> {
            ArrayList<Command> commands = currentApp.getParser().parse(textArea.getText().toLowerCase());
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

    private void addLSystemSliders(){
        int ind = textBoxVBox.getChildren().indexOf(textArea) + 1;
        textBoxVBox.getChildren().add(ind, lengthSlider.getSliderBox());
        ind++;
        textBoxVBox.getChildren().add(ind, angleSlider.getSliderBox());
        ind++;
        textBoxVBox.getChildren().add(ind, levelSlider.getSliderBox());
    }
    private void removeLSystemSliders(){
        textBoxVBox.getChildren().remove(lengthSlider.getSliderBox());
        textBoxVBox.getChildren().remove(angleSlider.getSliderBox());
        textBoxVBox.getChildren().remove(levelSlider.getSliderBox());
    }
    public void setCommands(ArrayList<Command> commands, AppView display) {
        currentApp.runApp(commands);
    }
    public boolean isCanvasClear(){
        if (canvasScreen.getShapes().getChildren().size() > 2){
            return false;
        }
        else return true;
    }
    private ComboBox<String> makeAppSelector(){
        ComboBox<String> c = new ComboBox<>(applicationLabels);
        c.setButtonCell(new ImageCell());
        c.setValue(applicationLabels.get(0));
        c.setOnAction(makeAppSelectionEventHandler());
        return c;
    }
    public class ImageCell extends ListCell<String> {
        protected void updateItem(String item, boolean empty){
            super.updateItem(item, empty);
            setGraphic(null);
            setText(null);
            if(item!=null){
                ImageView imageView = new ImageView(new Image(myResources.getString(item)));
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                setGraphic(imageView);
            }
        }
    }
}