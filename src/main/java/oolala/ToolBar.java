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

    /**
     *
     * @param property
     * @param handler
     * @return
     * @deprecated use {@link #ViewUtils.makeButton} instead
     */
    @Deprecated
    public Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }

    /**
     *
     * @param property
     * @param defaultValue
     * @param handler
     * @return
     * @deprecated use {@link #ViewUtils.makeTextField} instead
     */
    @Deprecated
    public TextField makeTextField(String property, String defaultValue, EventHandler<ActionEvent> handler) {
        TextField textField = new TextField(property);
        textField.setText(defaultValue);
        textField.setOnAction(handler);
        return textField;
    }

    /**
     *
     * @param items
     * @param handler
     * @return
     * @deprecated use {@link #ViewUtils.makeComboBoxArrayList} instead
     */
    @Deprecated
    public ComboBox<String> makeComboBoxArrayList(ArrayList<String> items, EventHandler<ActionEvent> handler) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items.get(0));//Default language
        comboBox.setOnAction(handler);
        return comboBox;
    }

    /**
     *
     * @param handler
     * @param defaultColor
     * @param tooltip
     * @return
     *
     * @deprecated use {@link #ViewUtils.makeColorPicker} instead
     */
    @Deprecated
    public ColorPicker makeColorPicker(EventHandler<ActionEvent> handler, Color defaultColor, String tooltip) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(defaultColor);
        colorPicker.setOnAction(handler);
        Tooltip.install(colorPicker, new Tooltip(myResources.getString(tooltip)));
        return colorPicker;
    }

}
