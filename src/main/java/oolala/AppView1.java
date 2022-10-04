package oolala;

import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppView1 extends AppView {

    private HBox logoRightHBox;
    private ComboBox<ImageView> logoImageSelector;

    public AppView1(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new TurtleDrawingModel(canvasScreen, myResources, "TurtleIcon", this);

        logoRightHBox = getRightToolBarHBox();
        logoImageSelector = makeBrushImageSelector();
        logoRightHBox.getChildren().add(0, logoImageSelector);
    }

    private ComboBox<ImageView> makeBrushImageSelector() {
        ComboBox<ImageView> imageSelector = viewUtils.makeImageSelector(iconLabels, "IconChange");
        imageSelector.setOnAction(event -> {
            currentApp.changeStamp(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }
}
