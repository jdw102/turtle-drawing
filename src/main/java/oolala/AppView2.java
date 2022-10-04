package oolala;

import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class AppView2 extends AppView {
    public AppView2(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new TurtleDrawingModel(this, myResources, "SimpleLeafStamp");
        textBoxVBox = makeLSystemTextBoxVBox();
        rightToolBarHBox = makeLSystemRightToolbarHBox();
        root.setLeft(textBoxVBox);
    }

    public VBox makeLSystemTextBoxVBox() {
        VBox vBox = makeTextBoxVBox();
        LSystemSlider slider = makeSliders(vBox);
        vBox.getChildren().add(2, slider.getSliderBox());
        return vBox;
    }

    public HBox makeLSystemRightToolbarHBox() {
        HBox hBox = makeRightToolbarHBox();
        makeBrushImageSelector();
        return hBox;
    }

    private void makeBrushImageSelector() {
        imageSelector = toolBar.makeImageSelector(stampLabels, "StampChange");
        imageSelector.setOnAction(event -> {
            currentApp.changeStamp(imageSelector.getValue().getImage().getUrl());
        });
    }
}
