package oolala.Views.ViewComponents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * A terminal abstract class that contains the text area for typing program, and the command history of the programs.
 * Will have subclasses that can add more specific features for different app views if necessary.
 *
 * @author Jerry Worthy
 */
public abstract class Terminal {
    public ResourceBundle myResources;
    private TextArea textArea;
    public VBox box;
    private ListView<String> recentlyUsed;

    public Terminal(ResourceBundle myResources) {
        this.myResources = myResources;
        textArea = new TextArea("");
        textArea.getStyleClass().add("text-area");
        textArea.setId("TerminalText");
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

    /**
     * A method to create the command history list view.
     *
     * @param maxHeight - The maximum allowed height of the list view.
     * @param handler   - The action to be performed when clicking a list item.
     * @return A ListView of strings
     * @author Jerry Worthy
     */
    public ListView<String> makeListView(int maxHeight, EventHandler<MouseEvent> handler) {
        ListView<String> listView = new ListView<String>();
        listView.setOnMouseClicked(handler);
        listView.setMaxHeight(maxHeight);
        listView.setId("CommandHistoryListView");
        return listView;
    }

    /**
     * A method to update the previous commands displayed by the list view.
     *
     * @param commands - The List of commands as strings.
     * @author Jerry Worthy
     */
    public void updateRecentlyUsed(List<String> commands) {
        Set<String> commandSet = new HashSet<>();
        commandSet.addAll(commands);
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

    public VBox getBox() {
        return box;
    }

    public void setText(String content) {
        textArea.setText(content);
    }

    public TextArea getTextArea() {
        return textArea;
    }
}
