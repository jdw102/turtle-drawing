package oolala.Views.ViewComponents;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ToolBar {
    ResourceBundle myResources;

    public ToolBar(ResourceBundle resources) {
        this.myResources = resources;
    }


    /**
     * @param property
     * @param handler
     * @return
     */
    public Button makeButton(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }

    /**
     * @param property
     * @param defaultValue
     * @param handler
     * @return
     */
    public TextField makeTextField(String property, String defaultValue, EventHandler<ActionEvent> handler) {
        TextField textField = new TextField(property);
        textField.setText(defaultValue);
        textField.setOnAction(handler);
        return textField;
    }

    /**
     * @param items
     * @param handler
     * @return
     */
    public ComboBox<String> makeComboBoxArrayList(ArrayList<String> items, EventHandler<ActionEvent> handler) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items.get(0));//Default language
        comboBox.setOnAction(handler);
        return comboBox;
    }

    public ComboBox<ImageView> makeImageSelector(List<String> labels, String title) {
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
        return c;
    }

    /**
     * @param handler
     * @param defaultColor
     * @param tooltip
     * @return
     */
    public ColorPicker makeColorPicker(EventHandler<ActionEvent> handler, Color defaultColor, String tooltip) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(defaultColor);
        colorPicker.setOnAction(handler);
        Tooltip.install(colorPicker, new Tooltip(myResources.getString(tooltip)));
        return colorPicker;
    }

    public Slider makeToggleBar(double min, double max, double value, int maxWidth) {
        Slider slider = new Slider(min, max, value);
        slider.setMaxWidth(maxWidth);
        slider.setOnMousePressed(event -> {
            if (slider.getValue() == 0) {
                slider.setValue(1);
            } else {
                slider.setValue(0);
            }
        });
        return slider;
    }
}
