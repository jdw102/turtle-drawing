package oolala.Views.ViewComponents;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import oolala.Models.AppModel;
import oolala.Parsers.LSystemParser;
import oolala.Views.AppView;
import oolala.Views.ViewUtils;

import java.util.ResourceBundle;

public class LSystemTerminal extends Terminal {
    public LSystemTerminal(int width, int height, ResourceBundle myResources, AppModel currentApp, AppView display, ViewUtils viewUtils) {
        super(width, height, myResources, currentApp, display, viewUtils);
        makeSliders(currentApp);
    }
    public void makeSliders(AppModel currentApp){
        LabeledSlider lengthSlider = new LabeledSlider(1, 100, 10, myResources.getString("LengthSlider"));
        LabeledSlider angleSlider = new LabeledSlider(1, 180, 30, myResources.getString("AngleSlider"));
        LabeledSlider levelSlider = new LabeledSlider(0, 5, 3, myResources.getString("LevelSlider"));
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
