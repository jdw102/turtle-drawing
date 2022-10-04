package oolala;

import javafx.stage.Stage;

public class AppView1 extends AppView {
    public AppView1(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new TurtleDrawingModel(this, myResources, "TurtleIcon");
    }

    private void makeBrushImageSelector() {
        imageSelector = toolBar.makeImageSelector(stampLabels, "IconChange");
        imageSelector.setOnAction(event -> {
            currentApp.changeStamp(imageSelector.getValue().getImage().getUrl());
        });
    }
}
