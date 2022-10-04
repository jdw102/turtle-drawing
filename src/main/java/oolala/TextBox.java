package oolala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;

import java.util.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oolala.Command.Command;

public class TextBox {
    private ResourceBundle myResources;
    private TextArea textArea;
    private VBox box;
    private HBox leftToolBar;
    private ListView<String> recentlyUsed;


    public TextBox(int textBoxWidth, int textBoxHeight, ResourceBundle myResources,AppModel currentApp, AppView display, ViewUtils viewUtils) {
        this.myResources = myResources;
        textArea = new TextArea("");
        textArea.setPrefSize(textBoxWidth, 3 * textBoxHeight / 4);
        EventHandler<MouseEvent> addLineEvent = event -> {
            addLine(recentlyUsed.getSelectionModel().getSelectedItem(), textArea);
        };
        HBox historyTitle = new HBox(new Label(myResources.getString("CommandHistory")));
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.getStyleClass().add("box");
        recentlyUsed = makeListView(200, addLineEvent);
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
        textArea.setOnKeyPressed(event -> {
            if (keyCombination.match(event)) {
                ArrayList<Command> commands = currentApp.getParser().parse(textArea.getText().toLowerCase());
                updateRecentlyUsed(commands, recentlyUsed);
                display.setCommands(commands);
            }
        });
        leftToolBar = makeLeftToolbarHBox(textBoxWidth, currentApp, display, viewUtils);
        box = new VBox(leftToolBar, textArea, historyTitle, recentlyUsed);
//        if (appName.equals("LSystem")){
//            makeSliders(currentApp);
//        }
    }

    public TextArea makeTextArea() {
        TextArea textArea = new TextArea("");
        return textArea;
    }

    public ListView<String> makeListView(int maxHeight, EventHandler<MouseEvent> handler){
        ListView<String> listView = new ListView<String>();
        listView.setOnMouseClicked(handler);
        listView.setMaxHeight(maxHeight);
        return listView;
    }

    public void updateRecentlyUsed(ArrayList<Command> commands, ListView recentlyUsed) {
        HashSet<String> commandSet = new HashSet<>();
        for (Command c : commands) {
            commandSet.add(c.toString());
        }
        ObservableList<String> displayedLines = FXCollections.observableArrayList(recentlyUsed.getItems());
        List<String> commandList = new ArrayList<>();
        for (String s : commandSet) {
            commandList.add(s);
        }
        int min = (commandList.size() < 6) ? 0 : commandList.size() - 6;
        for (int i = commandList.size() - 1; i >= min; i--) {
            if (!displayedLines.contains(commandList.get(i))) {
                displayedLines.add(0, commandList.get(i));
            }
        }
        recentlyUsed.setItems(displayedLines);
    }

    public void addLine(String s, TextArea textArea) {
        if (s != null) {
            textArea.appendText("\n");
            textArea.appendText(s);
        }
    }
    public void makeSliders(AppModel currentApp){
        LSystemSlider lengthSlider = new LSystemSlider(1, 100, 10, myResources.getString("LengthSlider"));
        LSystemSlider angleSlider = new LSystemSlider(1, 180, 30, myResources.getString("AngleSlider"));
        LSystemSlider levelSlider = new LSystemSlider(0, 5, 3, myResources.getString("LevelSlider"));
        EventHandler<MouseEvent> lengthChange = event -> {
            System.out.println("test");
            ( (LSystemParser) currentApp.getParser()).setDist((int) lengthSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> angleChange = event -> {
            ( (LSystemParser) currentApp.getParser()).setAng((int) angleSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> levelChange = event -> {
            ( (LSystemParser) currentApp.getParser()).setLevel((int) levelSlider.getSlider().getValue());
        };
        lengthSlider.setHandler(lengthChange);
        angleSlider.setHandler(angleChange);
        levelSlider.setHandler(levelChange);
        box.getChildren().add(2, lengthSlider.getSliderBox());
        box.getChildren().add(2, angleSlider.getSliderBox());
        box.getChildren().add(2, levelSlider.getSliderBox());
    }
    private HBox makeLeftToolbarHBox(int textBoxWidth, AppModel currentApp, AppView display, ViewUtils viewUtils) {
        EventHandler<ActionEvent> passCommands = makePassCommandsEventEventHandler(currentApp, display);
        EventHandler<ActionEvent> clearText = event -> textArea.clear();
        Button runButton = viewUtils.makeButton("RunButton", passCommands);
        Button clearTextButton = viewUtils.makeButton("ClearTextButton", clearText);
        Button fileOpenButton = viewUtils.makeButton("ImportButton", display.openFileChooserEventHandler());
        Button saveButton = viewUtils.makeButton("SaveButton", display.makeSaveFileEventHandler());
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
    private EventHandler<ActionEvent> makePassCommandsEventEventHandler(AppModel currentApp, AppView display) {
        EventHandler<ActionEvent> passCommands = event -> {
            ArrayList<Command> commands = currentApp.getParser().parse(textArea.getText().toLowerCase());
            updateRecentlyUsed(commands, recentlyUsed);
            currentApp.runApp(commands, display);
        };
        return passCommands;
    }
    public VBox getBox(){
        return box;
    }
    public void setText(String content){
        textArea.setText(content);
    }
    public String getText(){
        return textArea.getText();
    }
}
