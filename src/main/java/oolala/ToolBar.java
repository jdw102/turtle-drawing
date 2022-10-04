package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
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
     */
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
     */
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
     */
    public ComboBox<String> makeComboBoxArrayList(ArrayList<String> items, EventHandler<ActionEvent> handler) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items.get(0));//Default language
        comboBox.setOnAction(handler);
        return comboBox;
    }

    public ComboBox<ImageView> makeImageSelector(List<String> labels, String title){
        ComboBox<ImageView> c = new ComboBox<>();
        c.setButtonCell(new ListCell<ImageView>() {
            @Override protected void updateItem(ImageView item, boolean empty) {
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
        for (String s: labels){
            ImageView img = new ImageView(myResources.getString(s));
            img.setFitWidth(20);
            img.setFitHeight(20);
            c.getItems().add(img);
        };
        c.setValue(c.getItems().get(0));
        Tooltip t = new Tooltip(myResources.getString(title));
        Tooltip.install(c, t);
        return c;
    }

    /**
     *
     * @param handler
     * @param defaultColor
     * @param tooltip
     * @return
     *
     */
    public ColorPicker makeColorPicker(EventHandler<ActionEvent> handler, Color defaultColor, String tooltip) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(defaultColor);
        colorPicker.setOnAction(handler);
        Tooltip.install(colorPicker, new Tooltip(myResources.getString(tooltip)));
        return colorPicker;
    }
    public void makeSliders(VBox box, AppModel currentApp){
        LSystemSlider lengthSlider = new LSystemSlider(1, 100, 10, myResources.getString("LengthSlider"));
        LSystemSlider angleSlider = new LSystemSlider(1, 180, 30, myResources.getString("AngleSlider"));
        LSystemSlider levelSlider = new LSystemSlider(0, 5, 3, myResources.getString("LevelSlider"));
        EventHandler<MouseEvent> lengthChange = event -> {
            System.out.println("test");
            ( (LSystemParser) currentApp.getParser()).setDist((int) lengthSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> angleChange = event -> {
            ( (LSystemParser) currentApp.getParser()).setAng((int) angleSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> levelChange = event -> {
            ( (LSystemParser) currentApp.getParser()).setLevel((int) levelSlider.getSlider().getValue());
        };
        lengthSlider.setHandler(lengthChange);
        angleSlider.setHandler(angleChange);
        levelSlider.setHandler(levelChange);
        box.getChildren().add(2, lengthSlider.getSliderBox());
        box.getChildren().add(2, angleSlider.getSliderBox());
        box.getChildren().add(2, levelSlider.getSliderBox());
    }

}
