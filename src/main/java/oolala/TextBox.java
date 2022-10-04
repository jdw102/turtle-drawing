package oolala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.*;
import oolala.Command.Command;

public class TextBox {
    private ResourceBundle myResources;

    public TextBox(ResourceBundle myResources) {
        this.myResources = myResources;
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
}
