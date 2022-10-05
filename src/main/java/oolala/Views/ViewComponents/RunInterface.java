package oolala.Views.ViewComponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oolala.Command.Command;
import oolala.Models.AppModel;
import oolala.Views.AppView;
import oolala.Views.ViewUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public abstract class RunInterface {
    public ResourceBundle myResources;
    private TextArea textArea;
    public VBox box;
    private HBox leftToolBar;
    private ListView<String> recentlyUsed;


    public RunInterface(int width, int height, ResourceBundle myResources, AppModel currentApp, AppView display, ViewUtils viewUtils) {
        this.myResources = myResources;
        textArea = new TextArea("");
        textArea.getStyleClass().add("text-area");
        textArea.setPrefSize(width, 3 * height / 4);
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
        leftToolBar = makeLeftToolbarHBox(width, currentApp, display, viewUtils);
        box = new VBox(leftToolBar, textArea, historyTitle, recentlyUsed);
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
    private HBox makeLeftToolbarHBox(int width, AppModel currentApp, AppView display, ViewUtils viewUtils) {
        EventHandler<ActionEvent> passCommands = makePassCommandsEventEventHandler(currentApp, display);
        EventHandler<ActionEvent> clearText = event -> textArea.clear();
        Button runButton = viewUtils.makeButton("RunButton", passCommands);
        Button clearTextButton = viewUtils.makeButton("ClearTextButton", clearText);
        Button fileOpenButton = viewUtils.makeButton("ImportButton", display.openFileChooserEventHandler());
        Button saveButton = viewUtils.makeButton("SaveButton", display.makeSaveFileEventHandler());
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setMinWidth(width);
        saveButton.setMinWidth(width / 4);
        runButton.setMinWidth(width / 4);
        clearTextButton.setMinWidth(width / 4);
        fileOpenButton.setMinWidth(width / 4);
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
