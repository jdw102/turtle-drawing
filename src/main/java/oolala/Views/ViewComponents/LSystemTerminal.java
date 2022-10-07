package oolala.Views.ViewComponents;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import oolala.Parsers.LSystemParser;

import java.util.ResourceBundle;

public class LSystemTerminal extends Terminal {
    public LSystemTerminal(ResourceBundle myResources, LSystemParser lSystemParser) {
        super(myResources);
        makeSliders(lSystemParser);
    }
    public void makeSliders(LSystemParser lSystemParser){
        LabeledSlider lengthSlider = new LabeledSlider(1, 100, 10, myResources.getString("LengthSlider"));
        LabeledSlider angleSlider = new LabeledSlider(1, 180, 30, myResources.getString("AngleSlider"));
        LabeledSlider levelSlider = new LabeledSlider(0, 5, 3, myResources.getString("LevelSlider"));
        EventHandler<MouseEvent> lengthChange = event -> {
            System.out.println("test");
            lSystemParser.setDist((int) lengthSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> angleChange = event -> {
            lSystemParser.setAng((int) angleSlider.getSlider().getValue());
        };
        EventHandler<MouseEvent> levelChange = event -> {
            lSystemParser.setLevel((int) levelSlider.getSlider().getValue());
        };
        lengthSlider.setHandler(lengthChange);
        angleSlider.setHandler(angleChange);
        levelSlider.setHandler(levelChange);
        box.getChildren().add(1, lengthSlider.getSliderBox());
        box.getChildren().add(1, angleSlider.getSliderBox());
        box.getChildren().add(1, levelSlider.getSliderBox());
    }
    
}
