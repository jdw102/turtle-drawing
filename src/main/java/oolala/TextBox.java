package oolala;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.List;

import static oolala.OolalaView.myResources;

public class TextBox {
    private ListView<String> recentlyUsed;
    private TextArea textArea;
    private HBox buttonBox;
    private HBox historyTitle;
    private String historyText = "Command History";
    private Label historyLabel;
    private VBox box;
    private Button runButton;
    private Button fileOpenButton;
    private Button saveButton;
    private static final int DEFAULT_WIDTH = 275;
    private static final int DEFAULT_HEIGHT = 600;
    private int width;
    private int height;
    private ResourceBundle myResources;
    private ArrayList<String> labels = new ArrayList<String>(Arrays.asList("ImportButton", "SaveButton", "RunButton", "ClearTextButton"));

    public TextBox(ResourceBundle myResources){
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        recentlyUsed = new ListView<String>();
        recentlyUsed.setOnMouseClicked(event -> {
            addLine(recentlyUsed.getSelectionModel().getSelectedItem());
        });
        recentlyUsed.setMaxHeight(200);
        historyLabel = new Label(historyText);
        historyTitle = new HBox(historyLabel);
        historyTitle.setAlignment(Pos.CENTER);
        historyTitle.getStyleClass().add("box");

        this.myResources = myResources;
        textArea = new TextArea("");
        textArea.setPrefSize(width, 3 * height / 4);
        buttonBox = createButtonBox();
        box = new VBox();
        box.setPrefSize(width, 3 * height / 4);
        box.getChildren().add(buttonBox);
        box.getChildren().add(textArea);
        box.getChildren().add(historyTitle);
        box.getChildren().add(recentlyUsed);
    }

    public TextArea getTextArea() {
        return textArea;
    }
    public HBox createButtonBox(){
        runButton = makeButton("RunButton");

        Button clearTextButton = makeButton("ClearTextButton");
        clearTextButton.setOnAction(event -> textArea.clear());

        fileOpenButton = makeButton("ImportButton");

        HBox b = new HBox();
        b.setAlignment(Pos.CENTER);
        b.setMinWidth(width);

        saveButton = makeButton("SaveButton");
        saveButton.setMinWidth(width / 4);
        runButton.setMinWidth(width / 4);
        clearTextButton.setMinWidth(width / 4);
        fileOpenButton.setMinWidth(width / 4);
        b.getChildren().add(fileOpenButton);
        b.getChildren().add(saveButton);
        b.getChildren().add(runButton);
        b.getChildren().add(clearTextButton);

        return b;
    }
    public HBox getButtonBox(){
        return buttonBox;
    }
    private Button makeButton (String property) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        return result;
    }

    public void setLanguage(ResourceBundle resources) {
        myResources = resources;
        historyText = resources.getString("CommandHistory");
        historyLabel.setText(historyText);
        int i = 0;
        for (Node n : buttonBox.getChildren()) {
            updateButtonLanguage((Button) n, labels.get(i));
            i++;
        }
    }

    public void updateButtonLanguage(Button button, String property) {
        String label = myResources.getString(property);
        button.setText(label);
    }


    public VBox get() {
        return box;
    }

    public Button getRunButton() {
        return runButton;
    }
    public Button getFileChooserButton(){
        return fileOpenButton;
    }
    public Button getSaveButton(){
        return saveButton;
    }
    public void updateRecentlyUsed(){
        String text = textArea.getText();
        String[] lines = text.split("\n");
        List<String> linesList = new ArrayList<>();
        for (String s: lines){
            if (!s.isBlank() && !s.isEmpty()){
                linesList.add(s);
            }
        }
        ObservableList<String> displayedLines = FXCollections.observableArrayList(recentlyUsed.getItems());
        int min = (linesList.size() < 6) ? 0: linesList.size() - 6;
        for (int i = linesList.size() - 1; i >= min; i--){
            if (!displayedLines.contains(linesList.get(i))){
                displayedLines.add(0, linesList.get(i));
            }
        }
        recentlyUsed.setItems(displayedLines);
    }
    public void addLine(String s){
        if (s != null) {
            textArea.appendText("\n");
            textArea.appendText(s);
        }
    }
}
