package oolala;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LSystemAppView extends AppView {

    private HBox lSystemRightHBox;
    private ComboBox<ImageView> lSystemImageSelector;
    private TextBox lSystemTextBox;

    public LSystemAppView(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", this);
    }

    public Scene setUpScene() {
        lSystemRightHBox = makeRightToolbarHBox();
        imageSelector = makeBrushImageSelector();
        lSystemRightHBox.getChildren().add(0, imageSelector);
        textBox = new TextBox(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        textBox.makeSliders(currentApp);
        root.setLeft(textBox.getBox());
        root.setCenter(lSystemRightHBox);
        root.getChildren().add(canvasShapes);

        root.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }


    private ComboBox<ImageView> makeBrushImageSelector() {
        ComboBox<ImageView> imageSelector = viewUtils.makeImageSelector(stampLabels, "StampChange");
        imageSelector.setOnAction(event -> {
            currentApp.changeStamp(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }

}
