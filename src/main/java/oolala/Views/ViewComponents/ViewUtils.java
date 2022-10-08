package oolala.Views.ViewComponents;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ResourceBundle;

/**
 * A class that contains static methods for creating ui inputs such as buttons,
 * text fields, combo boxes, color pickers, and sliders across different view classes.
 *
 * @author Aditya Paul
 */
public class ViewUtils {
    static ResourceBundle myResources;

    public ViewUtils(ResourceBundle resources) {
        this.myResources = resources;
    }


    /**
     * @param property - The id of the button, used to get label.
     * @param handler - The action on click.
     * @return Created button.
     */
    public static Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        result.setId(property);
        return result;
    }

    /**
     * @param property - The id of the text field.
     * @param defaultValue - The default text.
     * @param handler - The action on enter.
     * @return Created text field.
     */
    public static TextField makeTextField(String property, String defaultValue, EventHandler<ActionEvent> handler) {
        TextField textField = new TextField(property);
        textField.setText(defaultValue);
        textField.setOnAction(handler);
        textField.setId(property);
        return textField;
    }
    /**
     * @param labels - The image urls used to create ImageView choices.
     * @param title - The id of the selector and used to get tooltip label.
     * @return Combobox of ImageViews that acts as image selector.
     */
    public static ComboBox<ImageView> makeImageSelector(List<String> labels, String title) {
        ComboBox<ImageView> c = new ComboBox<>();
        c.setButtonCell(new ListCell<ImageView>() {
            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    ImageView i = new ImageView(item.getImage().getUrl());
                    i.setFitHeight(item.getFitHeight());
                    i.setFitWidth(item.getFitWidth());
                    setGraphic(i);
                }
            }
        });
        for (String s : labels) {
            ImageView img = new ImageView(myResources.getString(s));
            img.setFitWidth(20);
            img.setFitHeight(20);
            c.getItems().add(img);
        }
        ;
        c.setValue(c.getItems().get(0));
        Tooltip t = new Tooltip(myResources.getString(title));
        Tooltip.install(c, t);
        c.setId(title);
        return c;
    }

    /**
     * @param handler - Event handler on color selection.
     * @param defaultColor - Default chosen color.
     * @param tooltip - The tooltip to install to color picker.
     * @return Created color picker.
     */
    public static ColorPicker makeColorPicker(EventHandler<ActionEvent> handler, Color defaultColor, String tooltip) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(defaultColor);
        colorPicker.setOnAction(handler);
        Tooltip.install(colorPicker, new Tooltip(myResources.getString(tooltip)));
        colorPicker.setId(tooltip);
        return colorPicker;
    }
    /**
     * @param value - The default value of the slider.
     * @param maxWidth - The maximum width of the slider.
     * @param name - The id of the slider.
     * @return Created binary slider.
     */
    public static Slider makeToggleBar(double value, int maxWidth, String name) {
        Slider slider = new Slider(0, 1, value);
        slider.setMaxWidth(maxWidth);
        slider.setOnMousePressed(event -> {
            if (slider.getValue() == 0) {
                slider.setValue(1);
            } else {
                slider.setValue(0);
            }
        });
        slider.setId(name);
        return slider;
    }
}
