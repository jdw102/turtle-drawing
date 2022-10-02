package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;

import java.util.ArrayList;

import static oolala.OolalaView.myResources;

public class TextBox {
    private TextArea textArea;
    private GridPane buttons;
    private ButtonBar buttonBar;
    private VBox box;
    private Button runButton;

    public TextBox(){
        textArea = new TextArea("");
        textArea.setPrefSize(200, 300);
        textArea.setMaxHeight(300);
        buttonBar = createButtonBar();
        box = new VBox();
        box.getChildren().add(buttonBar);
        box.getChildren().add(textArea);
    }
    public TextArea getTextArea(){
        return textArea;
    }
    public ButtonBar createButtonBar(){
        Button runButton = makeButton("RunButton", event -> System.out.println("test"));
        this.runButton = runButton;
        Button clearTextButton = makeButton("ClearTextButton", event -> textArea.clear());
        ButtonBar b = new ButtonBar();
        b.getButtons().add(runButton);
        b.getButtons().add(clearTextButton);
        return b;
    }
    public ButtonBar getButtonBar(){
        return buttonBar;
    }
    private Button makeButton (String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }

    public VBox get(){
        return box;
    }
    public Button getRunButton(){
        return runButton;
    }
}