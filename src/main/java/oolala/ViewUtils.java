package oolala;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ViewUtils {

  ResourceBundle myResources;

  public ViewUtils(ResourceBundle myResources) {
    this.myResources = myResources;
  }

  public TextField makeTextField(String property, String defaultValue, EventHandler<ActionEvent> handler) {
    TextField textField = new TextField(property);
    textField.setText(defaultValue);
    textField.setOnAction(handler);
    return textField;
  }

  public ComboBox<String> makeComboBoxArrayList(ArrayList<String> items, EventHandler<ActionEvent> handler) {
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.getItems().addAll(items);
    comboBox.setValue(items.get(0));//Default language
    comboBox.setOnAction(handler);
    return comboBox;
  }

  public ColorPicker makeColorPicker(EventHandler<ActionEvent> handler, Color defaultColor, String tooltip) {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setValue(defaultColor);
    colorPicker.setOnAction(handler);
    Tooltip.install(colorPicker, new Tooltip(myResources.getString(tooltip)));
    return colorPicker;
  }

  public Button makeButton(String property, EventHandler<ActionEvent> handler) {
    Button result = new Button();
    String label = myResources.getString(property);
    result.setText(label);
    result.setOnAction(handler);
    return result;
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
}
