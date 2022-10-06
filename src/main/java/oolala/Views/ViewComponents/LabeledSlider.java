package oolala.Views.ViewComponents;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class LabeledSlider {
    private Slider slider;
    private Label label;
    private HBox box;
    private Label name;

    public LabeledSlider(int min, int max, int curr, String title){
        slider = new Slider(min, max, curr);
        slider.valueProperty().addListener((obs, oldval, newVal) -> {
                    slider.setValue(newVal.intValue());
                    label.setText(Integer.toString((int) slider.getValue()));
        });
        slider.setShowTickMarks(true);
        slider.setId(title + "Slider");
        name = new Label(title);
        label = new Label(Integer.toString((int) slider.getValue()));
        box = new HBox(name, slider, label);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
    }
    public HBox getSliderBox(){
        return box;
    }
    public Slider getSlider(){
        return slider;
    }
    public Label getLabel(){
        return label;
    }
    public void setHandler(EventHandler<MouseEvent> handler){
        slider.setOnMouseReleased(handler);
    }
}
