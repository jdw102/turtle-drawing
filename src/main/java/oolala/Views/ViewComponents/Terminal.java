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
import javafx.scene.input.*;
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

public abstract class Terminal {
    public ResourceBundle myResources;
    private TextArea textArea;
    public VBox box;
    private ListView<String> recentlyUsed;

    public Terminal(ResourceBundle myResources) {
        this.myResources = myResources;
        textArea = new TextArea("");
        textArea.getStyleClass().add("text-area");
        EventHandler<MouseEvent> addLineEvent = event -> {
            addLine(recentlyUsed.getSelectionModel().getSelectedItem(), textArea);
        };
        Label historyLabel = new Label(myResources.getString("CommandHistory"));
        HBox historyTitle = new HBox(historyLabel);
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.getStyleClass().add("box");
        recentlyUsed = makeListView(200, addLineEvent);

        box = new VBox(textArea, historyTitle, recentlyUsed);
        box.setSpacing(5);
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

    public ListView<String> getRecentlyUsed() {
        return recentlyUsed;
    }

    public VBox getBox(){
        return box;
    }
    public void setText(String content){
        textArea.setText(content);
    }
    public TextArea getTextArea(){
        return textArea;
    }
}
