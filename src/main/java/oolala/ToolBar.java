package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.ResourceBundle;



public class ToolBar {
    ResourceBundle myResources;

    public ToolBar(ResourceBundle resources){
        this.myResources = resources;
    }

    public Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
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

}
