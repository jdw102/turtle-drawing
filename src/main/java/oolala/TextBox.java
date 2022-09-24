package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import java.util.ArrayList;

import static oolala.OolalaView.myResources;

public class TextBox {
    private TextArea textArea;
    private GridPane buttons;
    private HBox buttonBox;
    private VBox box;
    private Button runButton;
    private Button fileOpenButton;

    public TextBox(){
        textArea = new TextArea("");
        textArea.setPrefSize(300, 400);
        buttonBox = createButtonBox();
        box = new VBox();
        box.getChildren().add(buttonBox);
        box.getChildren().add(textArea);
    }
    public TextArea getTextArea(){
        return textArea;
    }
    public HBox createButtonBox(){
        runButton = makeButton("RunButton");

        Button clearTextButton = makeButton("ClearTextButton");
        clearTextButton.setOnAction(event -> textArea.clear());

        fileOpenButton = makeButton("FileChooser");

        HBox b = new HBox();
        b.setAlignment(Pos.CENTER);
        b.setMinWidth(300);

        runButton.setMinWidth(100);
        clearTextButton.setMinWidth(100);
        fileOpenButton.setMinWidth(100);
        b.getChildren().add(runButton);
        b.getChildren().add(clearTextButton);
        b.getChildren().add(fileOpenButton);
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

    public VBox get(){
        return box;
    }
    public Button getRunButton(){
        return runButton;
    }
    public Button getFileChooserButton(){
        return fileOpenButton;
    }
}
