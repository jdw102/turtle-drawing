package oolala.Views.ViewComponents;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import oolala.Parsers.LSystemParser;

import java.util.ResourceBundle;

/**
 * A terminal for the LSystem app. It adds the length, angle, and level slider inputs to the terminal.
 *
 * @author Jerry Worthy
 */
public class LSystemTerminal extends Terminal {
    public LSystemTerminal(ResourceBundle myResources, LSystemParser lSystemParser) {
        super(myResources);
        makeSliders(lSystemParser);
    }
    /**
     * A method to create the sliders that control parser settings.
     *
     * @param lSystemParser - The parser that will have its settings changed by the sliders.
     * @author Jerry Worthy
     */
    public void makeSliders(LSystemParser lSystemParser){
    LabeledSlider lengthSlider = new LabeledSlider(1, 100, 10, myResources.getString("LengthSlider"), "LengthSlider");
    LabeledSlider angleSlider = new LabeledSlider(1, 180, 30, myResources.getString("AngleSlider"),  "AngleSlider");
    LabeledSlider levelSlider = new LabeledSlider(0, 5, 3, myResources.getString("LevelSlider"), "LevelSlider");
    LabeledSlider LSFSlider = new LabeledSlider(0, 5, 1, myResources.getString("LSFSlider"), "LSFSlider");
    lengthSlider.getSlider().valueProperty().addListener((obs, oldval, newVal) -> {
          lengthSlider.getSlider().setValue(newVal.intValue());
          lengthSlider.getLabel().setText(Integer.toString((int) lengthSlider.getSlider().getValue()));
          lSystemParser.setDist(lengthSlider.getSlider().getValue());
      });
      angleSlider.getSlider().valueProperty().addListener((obs, oldval, newVal) -> {
          angleSlider.getSlider().setValue(newVal.intValue());
          angleSlider.getLabel().setText(Integer.toString((int) angleSlider.getSlider().getValue()));
          lSystemParser.setAng(angleSlider.getSlider().getValue());
      });
      levelSlider.getSlider().valueProperty().addListener((obs, oldval, newVal) -> {
          levelSlider.getSlider().setValue(newVal.intValue());
          levelSlider.getLabel().setText(Integer.toString((int) levelSlider.getSlider().getValue()));
          lSystemParser.setLevel(levelSlider.getSlider().getValue());
      });
      LSFSlider.getSlider().valueProperty().addListener((obs, oldval, newVal) -> {
        LSFSlider.getSlider().setValue(newVal.intValue());
        LSFSlider.getLabel().setText(Integer.toString((int) LSFSlider.getSlider().getValue()));
        lSystemParser.setLsf((int) LSFSlider.getSlider().getValue());
      });
      box.getChildren().add(1, lengthSlider.getSliderBox());
      box.getChildren().add(1, angleSlider.getSliderBox());
      box.getChildren().add(1, levelSlider.getSliderBox());
      box.getChildren().add(1, LSFSlider.getSliderBox());
    }
    
}
