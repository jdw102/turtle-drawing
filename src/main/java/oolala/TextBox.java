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

import javax.imageio.ImageIO;

import static oolala.OolalaView.myResources;

public class TextBox {
    private Parser parser = new Parser();
    private TextArea textArea;
    private GridPane buttons;
    private ButtonBar buttonBar;
    private GridPane box;

    public TextBox(){
        textArea = new TextArea("");
        textArea.setPrefSize(200, 300);
        textArea.setMaxHeight(300);
        buttonBar = createButtonBar();
        box = new GridPane();
        box.add(buttonBar, 0, 0);
        box.add(textArea, 0, 1);
    }
    public TextArea getTextArea(){
        return textArea;
    }
    public ButtonBar createButtonBar(){
        Button runButton = makeButton("RunButton", event -> runScript());
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
    public void runScript(){
        System.out.println(textArea.getText());
    }
    public GridPane get(){
        return box;
    }
}